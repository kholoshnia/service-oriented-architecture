import { Coordinates } from 'models/organization';
import { getUniqueMinMax, normalize } from 'utils/math-helpers';

export type NormalizableItem = {
  id: number;
  coordinates: Coordinates;
};

export const getRanges = <
  First extends NormalizableItem,
  Second extends NormalizableItem
>(
  first: First[],
  second: Second[],
  width: number,
  height: number
) => {
  const data = [...first, ...second];
  const [minX, maxX] = getUniqueMinMax(data, width, o => o.coordinates.x);
  const [minY, maxY] = getUniqueMinMax(data, height, o => o.coordinates.y);
  return { minX, maxX, minY, maxY };
};

export type Normalized<T> = T & {
  normalized: Coordinates;
};

export const normalizeCoordinates = <
  First extends NormalizableItem,
  Second extends NormalizableItem
>(
  first: First[],
  second: Second[],
  width = 0,
  height = 0
): [Normalized<First>[], Normalized<Second>[]] => {
  const { minX, maxX, minY, maxY } = getRanges(first, second, width, height);

  const normalizeData = <Item extends NormalizableItem>(
    data: Item[]
  ): Normalized<Item>[] =>
    data.map(item => ({
      ...item,
      normalized: {
        x: normalize(item.coordinates.x, minX, maxX, width),
        y: normalize(item.coordinates.y, minY, maxY, height),
      },
    }));

  return [normalizeData(first), normalizeData(second)];
};

export const getInitialCoordinates = <
  First extends NormalizableItem,
  Second extends NormalizableItem
>(
  x: number,
  y: number,
  first: Normalized<First>[],
  second: Normalized<Second>[],
  width = 0,
  height = 0
): Coordinates => {
  const { maxX, maxY } = getRanges(first, second, width, height);
  return {
    x: normalize(x, 0, width, maxX),
    y: normalize(y, 0, height, maxY),
  };
};

export const getElementSize = (el: HTMLElement) => {
  const { width, height } = getComputedStyle(el);
  return { width: parseFloat(width), height: parseFloat(height) };
};
