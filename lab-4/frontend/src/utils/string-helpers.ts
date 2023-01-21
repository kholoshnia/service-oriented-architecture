export const shortenString = (string: string, max = 10) => {
  const str = String(string);
  return str.length < max ? max : str.substring(0, max) + '...';
};
