const clearValues = obj => {
  Object.keys(obj).forEach(key => {
    if (typeof obj[key] === 'object') {
      clearValues(obj);
      return;
    }

    if (typeof obj[key] === 'string' && obj[key]?.trim()?.length === 0) {
      obj[key] = undefined;
    }
  });
};

export default clearValues;
