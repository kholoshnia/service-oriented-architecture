import { XMLBuilder, XMLParser } from 'fast-xml-parser';

const transformArrays = data => {
  Object.keys(data).forEach(key => {
    if (typeof data[key] === 'object') {
      if (data[key] && data[key][key]) {
        data[key] = Array.isArray(data[key][key])
          ? data[key][key]
          : [data[key][key]];
        data[key].map(transformArrays);
      } else {
        transformArrays(data[key]);
      }
    }
  });
};

const xmlParser = new XMLParser();

export const parseXml = (xml?: string | Buffer) => {
  if (!xml) return undefined;

  const parsed = xmlParser.parse(xml);
  const result = parsed[Object.keys(parsed)[0]];

  if (result.data === '') {
    result.data = [];
  } else if (result.data && result.data.data) {
    result.data = Array.isArray(result.data.data)
      ? result.data.data
      : [result.data.data];
  }

  transformArrays(result);
  return result;
};

const xmlBuilder = new XMLBuilder({});

export const toXml = (obj: any) => {
  if (!obj) return undefined;

  Object.keys(obj).forEach(key => {
    if (Array.isArray(obj[key])) {
      obj[key] = { [key]: obj[key] };
    }
  });

  return `<new>${xmlBuilder.build(obj)}</new>`;
};
