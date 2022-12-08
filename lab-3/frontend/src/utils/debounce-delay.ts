const debounceDelay = (callback: Function, delay: number) => {
  let started = Date.now();
  return (...args) => {
    setTimeout(() => {
      if (Date.now() - started > delay) {
        // eslint-disable-next-line prefer-spread
        callback.apply(null, args);
        started = Date.now();
      }
    }, delay);
  };
};

export default debounceDelay;
