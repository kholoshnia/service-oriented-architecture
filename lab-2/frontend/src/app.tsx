import React, { FC } from 'react';

import 'antd/dist/antd.css';
import './App.css';
import { Radio } from 'antd';
import { Typography } from 'antd';

const { Title } = Typography;

const App: FC = () => {
  return (
    <div className="app">
      <Title>h1. Ant Design</Title>

      <Radio.Group defaultValue="a" buttonStyle="solid">
        <Radio.Button value="a">Hangzhou</Radio.Button>
        <Radio.Button value="b">Shanghai</Radio.Button>
        <Radio.Button value="c">Beijing</Radio.Button>
        <Radio.Button value="d">Chengdu</Radio.Button>
      </Radio.Group>
    </div>
  );
}

export default App;
