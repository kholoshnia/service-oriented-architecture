const setIdKeys = (items: { id: any }[]) =>
  items.map((item: any) => {
    item.key = item.id;
    return item;
  });

export default setIdKeys;
