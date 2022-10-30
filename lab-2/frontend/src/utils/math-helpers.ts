export const normalize = (
  value: number,
  min: number,
  max: number,
  range: number
) => ((value - min) / (max - min)) * range;

export const getUniqueMinMax = <Item extends { id: number }>(
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

export const randomRange = (min: number, max: number) =>
  Math.random() * (max - min) + min;
