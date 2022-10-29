import { FC, useEffect, useRef, useState } from 'react';
import './truck-with-boxes.scss';

import { Engine, Render, Runner, Bodies, Composite, Body } from 'matter-js';

import box from 'assets/images/box.png';
import truck from 'assets/images/truck.png';
import { getSize } from 'utils/map-helpers';

const getRandomRange = (min, max): number => Math.random() * (max - min) + min;

type TruckWithBoxesProps = {
  boxesCount: number;
};

const TruckWithBoxes: FC<TruckWithBoxesProps> = ({ boxesCount }) => {
  const ref = useRef<HTMLDivElement>(null);
  const truckRef = useRef<HTMLImageElement>(null);

  const [world, setWorld] = useState<any>();
  const [boxes, setBoxes] = useState<any[]>([]);

  useEffect(() => {
    if (!ref.current) return;
    const { width, height } = getSize(ref.current);

    const engine = Engine.create();
    const render = Render.create({
      element: ref.current,
      engine: engine,
      options: {
        width,
        height,
        wireframes: false,
        background: 'transparent',
        wireframeBackground: 'transparent',
      },
    });

    const bottom = Bodies.rectangle(width / 2, height, width, 10, {
      isStatic: true,
      render: {
        visible: false,
      },
    });
    const left = Bodies.rectangle(0, height, 10, height * 2, {
      isStatic: true,
      render: {
        visible: false,
      },
    });
    const right = Bodies.rectangle(width, height, 10, height * 2, {
      isStatic: true,
      render: {
        visible: false,
      },
    });

    Composite.add(engine.world, [bottom, left, right]);
    setWorld(engine.world);

    Render.run(render);
    const runner = Runner.create();
    Runner.run(runner, engine);

    return () => {
      if (ref.current) ref.current.innerHTML = '';
    };
  }, []);

  useEffect(() => {
    if (!ref.current || !world) return;

    let timeoutId;
    if (truckRef.current && boxesCount !== 0) {
      truckRef.current.style.opacity = '0.5';
      timeoutId = setTimeout(() => {
        if (truckRef.current) {
          truckRef.current.style.opacity = '1';
        }
      }, 3000);
    }

    const { width } = getSize(ref.current);

    const createRandomBox = () => {
      const boxX = getRandomRange(80, width - 80);
      const boxSize = getRandomRange(50, 100);
      const rect = Bodies.rectangle(boxX, -60, boxSize, (boxSize * 400) / 512, {
        render: {
          sprite: {
            texture: box,
            xScale: boxSize / 512,
            yScale: boxSize / 512,
          },
          opacity: 1,
        },
      });
      Body.rotate(rect, getRandomRange(-30, 30));
      return rect;
    };

    if (boxesCount > boxes.length) {
      const diff = boxesCount - boxes.length;
      const newBoxes = Array.from(Array(diff).keys()).map(() => {
        const box = createRandomBox();
        Composite.add(world, [box]);
        return box;
      });
      setBoxes(boxes.concat(newBoxes));
    } else if (boxesCount < boxes.length) {
      boxes
        .slice(boxesCount, boxes.length)
        .forEach(box => Composite.remove(world, box));
      setBoxes(boxes.slice(0, boxesCount));
    }

    return () => timeoutId && clearTimeout(timeoutId);
  }, [world, boxesCount]);

  return (
    <div className="truck-with-boxes">
      <div className="truck-with-boxes__truck">
        {boxesCount === 0 && (
          <span className="truck-with-boxes__text">Select some products!</span>
        )}
        <img
          ref={truckRef}
          src={truck}
          alt="Truck"
          className="truck-with-boxes__truck-image"
        />
        <div ref={ref} className="truck-with-boxes__boxes" />
      </div>
    </div>
  );
};

export default TruckWithBoxes;
