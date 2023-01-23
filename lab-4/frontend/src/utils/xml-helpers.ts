import { XMLBuilder, XMLParser } from 'fast-xml-parser';

const deepArraysUnwrap = data => {
  Object.keys(data).forEach(key => {
    if (typeof data[key] === 'object') {
      if (data[key] && data[key][key]) {
        data[key] = Array.isArray(data[key][key])
          ? data[key][key]
          : [data[key][key]];
        data[key].map(deepArraysUnwrap);
      } else {
        deepArraysUnwrap(data[key]);
      }
    }
  });
};

const xmlParser = new XMLParser();

export const parseXml = (xml?: string | Buffer) => {
  if (!xml) return undefined;
  const parsed = xmlParser.parse(xml);
  delete parsed['?xml'];
  const result = parsed[Object.keys(parsed)[0]];

  if (result.data === '') {
    result.data = [];
  }

  deepArraysUnwrap(result);
  return result;
};

const deepArraysWrap = data => {
  Object.keys(data).forEach(key => {
    if (Array.isArray(data[key])) {
      data[key] = { [key]: data[key] };
    }
  });
};

const xmlBuilder = new XMLBuilder({});

export const toXml = (data: any) => {
  if (!data) return undefined;

  deepArraysWrap(data);
  return `<new>${xmlBuilder.build(data)}</new>`;
};
