interface GameState {
  cells: Cell[];
  instructions: string;
  nextPlayer: string;
  winner: string;
  gameOver: boolean;
}

interface Cell {
  text: string;
  playable: boolean;
  x: number;
  y: number;
}

export type { GameState, Cell }
