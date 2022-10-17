export const shortenString = (string: string, max = 10) =>
  string.length < max ? max : string.substring(0, max) + '...';
