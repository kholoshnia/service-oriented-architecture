import store1 from 'assets/images/store-icons/store-1.png';
import store2 from 'assets/images/store-icons/store-2.png';
import store3 from 'assets/images/store-icons/store-3.png';
import store4 from 'assets/images/store-icons/store-4.png';
import store5 from 'assets/images/store-icons/store-5.png';
import { Organization } from 'models/organization';

export type WithIcon<T> = T & {
  iconSrc: string;
};

const sizeToIcon = (size: number) => {
  if (size <= 5) return store1;
  if (size <= 7) return store2;
  if (size <= 10) return store3;
  if (size <= 12) return store4;
  return store5;
};

const withIcons = (organizations: Organization[]): WithIcon<Organization>[] =>
  organizations.map(o => ({
    ...o,
    iconSrc: sizeToIcon(String(o.name).length),
  }));

export default withIcons;
