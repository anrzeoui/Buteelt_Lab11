import React from 'react';
import './App.css';
import { GameState, Cell } from './game';
import BoardCell from './Cell';

interface Props { }

class App extends React.Component<Props, GameState> {
  private initialized: boolean = false;

  constructor(props: Props) {
    super(props);
    this.state = {
      cells: [],
      instructions: 'Loading game...',
      nextPlayer: 'X',
      winner: '',
      gameOver: false,
    };
  }

  updateGame = async (url: string) => {
    const response = await fetch(url);
    const json = await response.json();
    this.setState(json);
  }

  newGame = async () => {
    await this.updateGame('/newgame');
  }

  play(x: number, y: number): React.MouseEventHandler {
    return async (e) => {
      e.preventDefault();
      await this.updateGame(`/play?x=${x}&y=${y}`);
    }
  }

  undo = async () => {
    await this.updateGame('/undo');
  }

  createCell(cell: Cell, index: number): React.ReactNode {
    if (cell.playable) {
      return (
        <div key={index} className="cell-wrapper">
          <a href='/' className="cell-link" onClick={this.play(cell.x, cell.y)}>
            <BoardCell cell={cell}></BoardCell>
          </a>
        </div>
      );
    }

    return (
      <div key={index} className="cell-wrapper">
        <BoardCell cell={cell}></BoardCell>
      </div>
    );
  }

  componentDidMount(): void {
    if (!this.initialized) {
      this.newGame();
      this.initialized = true;
    }
  }

  render(): React.ReactNode {
    return (
      <main id="game">
        <div id="instructions">{this.state.instructions}</div>
        <div id="board">
          {this.state.cells.map((cell, i) => this.createCell(cell, i))}
        </div>
        <div id="bottombar">
          <button onClick={this.newGame}>New Game</button>
          <button onClick={this.undo}>Undo</button>
        </div>
      </main>
    );
  }
}

export default App;
