import { XMLBuilder, XMLParser } from 'fast-xml-parser';

const xmlParser = new XMLParser();

export const parseXml = (xml: string | Buffer) => {
  const parsed = xmlParser.parse(xml);
  const result = parsed[Object.keys(parsed)[0]];

  if (result.data === '') {
    result.data = [];
  } else if (result.data && result.data.data) {
    result.data = Array.isArray(result.data.data)
      ? result.data.data
      : [result.data.data];
  }

  return result;
};

const xmlBuilder = new XMLBuilder({});

export const toXml = (obj: any) => {
  return `<new>${xmlBuilder.build(obj)}</new>`;
};
