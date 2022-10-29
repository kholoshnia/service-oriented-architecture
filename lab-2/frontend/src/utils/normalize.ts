const normalize = (value: number, min: number, max: number, range: number) =>
  ((value - min) / (max - min)) * range;

export default normalize;
