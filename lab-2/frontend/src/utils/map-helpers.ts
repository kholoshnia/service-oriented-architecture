import { Coordinates } from 'models/organization';
import normalize from 'utils/normalize';

type NormalizableItem = {
  id: number;
  coordinates: Coordinates;
};

export const getRange = <Item extends { id: number }>(
  data: Item[],
  range: number,
  getValue: (data: Item) => number
): [number, number] => {
  if (data.length === 0) return [0, range];

  const minBy = data.reduce((p, v) => (getValue(p) < getValue(v) ? p : v));
  const exceptMinBy = data.filter(o => o.id !== minBy.id);

  if (exceptMinBy.length === 0) return [getValue(minBy), range];

  const maxBy = exceptMinBy.reduce((p, v) =>
    getValue(p) >= getValue(v) ? p : v
  );

  return [getValue(minBy), getValue(maxBy)];
};

export type Normalized<T> = T & { normalized: Coordinates };

const normalizeCoordinates = <Item extends NormalizableItem>(
  data: Item[],
  width = 0,
  height = 0
): Normalized<Item>[] => {
  const [minX, maxX] = getRange(data, width, o => o.coordinates.x);
  const [minY, maxY] = getRange(data, height, o => o.coordinates.y);

  return data.map(item => ({
    ...item,
    normalized: {
      x: normalize(item.coordinates.x, minX, maxX, width),
      y: normalize(item.coordinates.y, minY, maxY, height),
    },
  }));
};

export default normalizeCoordinates;

export const getSize = (el: HTMLElement) => {
  const { width, height } = getComputedStyle(el);
  return { width: parseFloat(width), height: parseFloat(height) };
};
