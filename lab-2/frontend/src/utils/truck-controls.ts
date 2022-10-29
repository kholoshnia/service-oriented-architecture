let target, maxX, maxY;

const speed = 2.5;

const addLeft = left => {
  const value = parseFloat(target.style.left) + left;
  if (value >= 0 && value <= maxX) target.style.left = `${value}px`;
};

const addTop = top => {
  const value = parseFloat(target.style.top) + top;
  if (value >= 0 && value <= maxY) target.style.top = `${value}px`;
};

const moveRight = () => addLeft(speed);
const moveLeft = () => addLeft(-speed);
const moveDown = () => addTop(speed);
const moveUp = () => addTop(-speed);

let deg = 180;
const rotate = newDeg => {
  let rotation;
  deg = deg || 0;
  rotation = deg % 360;
  if (rotation < 0) rotation += 360;
  if (rotation < 180 && newDeg > rotation + 180) deg -= 360;
  if (rotation >= 180 && newDeg <= rotation - 180) deg += 360;
  deg += newDeg - rotation;
  target.style.transform = `translate(-50%, -50%) rotate(${deg}deg)`;
};

const directions = {
  left: false,
  right: false,
  up: false,
  down: false,
};

export const onKeyDownTruck = (e, greatestX, greatestY) => {
  target = e.target;
  maxX = greatestX;
  maxY = greatestY;

  if ('code' in e) {
    switch (e.code) {
      case 'Unidentified':
        break;
      case 'ArrowRight':
      case 'KeyD':
        directions.right = true;
        break;
      case 'ArrowLeft':
      case 'KeyA':
        directions.left = true;
        break;
      case 'ArrowUp':
      case 'KeyW':
        directions.up = true;
        break;
      case 'ArrowDown':
      case 'KeyS':
        directions.down = true;
        break;
      default:
        break;
    }
  }

  if (e.keyCode === 39) {
    directions.right = true;
  } else if (e.keyCode === 37) {
    directions.left = true;
  }
  if (e.keyCode === 40) {
    directions.down = true;
  } else if (e.keyCode === 38) {
    directions.up = true;
  }
};

export const onKeyUpTruck = e => {
  target = e.target;

  if ('code' in e) {
    switch (e.code) {
      case 'Unidentified':
        break;
      case 'ArrowRight':
      case 'KeyD':
        directions.right = false;
        break;
      case 'ArrowLeft':
      case 'KeyA':
        directions.left = false;
        break;
      case 'ArrowUp':
      case 'KeyW':
        directions.up = false;
        break;
      case 'ArrowDown':
      case 'KeyS':
        directions.down = false;
        break;
      default:
        break;
    }
  }

  if (e.keyCode === 39) {
    directions.right = false;
  } else if (e.keyCode === 37) {
    directions.left = false;
  }
  if (e.keyCode === 40) {
    directions.down = false;
  } else if (e.keyCode === 38) {
    directions.up = false;
  }
};

const draw = () => {
  if (target) {
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

    if (directions.up && directions.right) {
      rotate(45);
    } else if (directions.right && directions.down) {
      rotate(135);
    } else if (directions.down && directions.left) {
      rotate(-135);
    } else if (directions.left && directions.up) {
      rotate(-45);
    } else if (directions.right) {
      rotate(90);
    } else if (directions.left) {
      rotate(-90);
    } else if (directions.down) {
      rotate(180);
    } else if (directions.up) {
      rotate(0);
    }
  }

  requestAnimationFrame(draw);
};

draw();
