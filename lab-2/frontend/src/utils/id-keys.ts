const setIdKeys = <Item>(
  items: Item[],
  getKey = (item: Item) => (item as any).id
) =>
  items.map((item: Item) => {
    (item as any).key = getKey(item);
    return item;
  });

export default setIdKeys;
