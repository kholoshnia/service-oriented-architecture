export const normalize = (
  value: number,
  min: number,
  max: number,
  range: number
) => ((value - min) / (max - min)) * range;

export const getUniqueMinMax = <Item>(
  data: Item[],
  range: number,
  getValue: (data: Item) => number,
  getId: (data: Item) => any
): [number, number] => {
  if (data.length === 0) return [0, range];

  const minBy = data.reduce((p, v) => (getValue(p) < getValue(v) ? p : v));
  const exceptMinBy = data.filter(o => getId(o) !== getId(minBy));
  const minValue = getValue(minBy);

  if (data.length === 1) {
    if (minValue < 0) return [minValue, range];
    if (minValue >= range) return [0, minValue];
    return [0, range];
  }

  const maxBy = exceptMinBy.reduce((p, v) =>
    getValue(p) >= getValue(v) ? p : v
  );
  const maxValue = getValue(maxBy);

  if (minValue <= 0 && maxValue >= range) return [minValue, maxValue];
  if (minValue <= 0 && maxValue <= range) return [minValue, range];
  if (minValue >= 0 && maxValue >= range) return [0, maxValue];
  return [0, range];
};
