import { KeyboardEvent } from 'react';

let target: HTMLElement;
let xBoundary: number;
let yBoundary: number;

const directions = {
  left: false,
  right: false,
  up: false,
  down: false,
};

export type onKeyParams = {
  event: KeyboardEvent<any>;
  target?: HTMLElement | null;
  maxX: number;
  maxY: number;
};

const onKeyPressed = (
  { event, target: currentTarget, maxX, maxY }: onKeyParams,
  pressed: boolean
) => {
  if (!currentTarget) return;
  target = currentTarget;
  xBoundary = maxX;
  yBoundary = maxY;

  switch (event.key) {
    case 'ArrowRight':
    case 'd':
      directions.right = pressed;
      break;
    case 'ArrowLeft':
    case 'a':
      directions.left = pressed;
      break;
    case 'ArrowUp':
    case 'w':
      directions.up = pressed;
      break;
    case 'ArrowDown':
    case 's':
      directions.down = pressed;
      break;
    default:
      break;
  }
};

export const onKeyDownTruck = (params: onKeyParams) =>
  onKeyPressed(params, true);

export const onKeyUpTruck = (params: onKeyParams) =>
  onKeyPressed(params, false);

const speed = 2.5;

const addToX = (left: number) => {
  const value = parseFloat(target.style.left) + left;
  if (value >= 0 && value <= xBoundary) target.style.left = `${value}px`;
};

const addToY = (top: number) => {
  const value = parseFloat(target.style.top) + top;
  if (value >= 0 && value <= yBoundary) target.style.top = `${value}px`;
};

const moveRight = () => addToX(speed);
const moveLeft = () => addToX(-speed);
const moveDown = () => addToY(speed);
const moveUp = () => addToY(-speed);

let deg = 180;
const rotateShortest = newDeg => {
  let rotation;
  deg = deg || 0;
  rotation = deg % 360;
  if (rotation < 0) rotation += 360;
  if (rotation < 180 && newDeg > rotation + 180) deg -= 360;
  if (rotation >= 180 && newDeg <= rotation - 180) deg += 360;
  deg += newDeg - rotation;
  target.style.transform = `translate(-50%, -50%) rotate(${deg}deg)`;
};

const animateMovement = () => {
  if (directions.right) {
    moveRight();
  } else if (directions.left) {
    moveLeft();
  }
  if (directions.down) {
    moveDown();
  } else if (directions.up) {
    moveUp();
  }
};

const animateRotation = () => {
  if (directions.up && directions.right) {
    rotateShortest(45);
  } else if (directions.right && directions.down) {
    rotateShortest(135);
  } else if (directions.down && directions.left) {
    rotateShortest(-135);
  } else if (directions.left && directions.up) {
    rotateShortest(-45);
  } else if (directions.right) {
    rotateShortest(90);
  } else if (directions.left) {
    rotateShortest(-90);
  } else if (directions.down) {
    rotateShortest(180);
  } else if (directions.up) {
    rotateShortest(0);
  }
};

const animate = () => {
  if (target) {
    animateMovement();
    animateRotation();
  }

  requestAnimationFrame(animate);
};

animate();
