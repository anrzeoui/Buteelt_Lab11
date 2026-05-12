import React from 'react';
import { Cell } from './game';

interface Props {
  cell: Cell
}

class BoardCell extends React.Component<Props> {
  render(): React.ReactNode {
    const playable = this.props.cell.playable ? 'playable' : '';
    return (
      <div className={`cell ${playable}`} aria-label={`cell ${this.props.cell.x}, ${this.props.cell.y}`}>
        {this.props.cell.text}
      </div>
    )
  }
}

export default BoardCell;
