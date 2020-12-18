import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.SET;

public class Solver {
  private static class Move {
    public Move previous;
    public Board board;
    public int numMoves = 0;

    public Move(Board board) {
      this.board = board;
    }

    public Move nextMove(Board nextBoard) {
      Move nextMove = new Move(nextBoard);
      nextMove.previous = this;
      nextMove.numMoves = numMoves + 1;
      return nextMove;
    }

    public Iterable<Move> nextMoves() {
      List<Move> nextMoves = new ArrayList<Move>();
      for (Board neighbor: board.neighbors()) {
        if (previous == null || !neighbor.equals(previous.board)) {
          nextMoves.add(nextMove(neighbor));
        }
      }
      return nextMoves;
    }
  }

  private static class MoveComparator implements Comparator<Move> {
    public enum HeuristicStrategy {
      ManhattanDistance,
      HammingDistance,
    }

    public final HeuristicStrategy strategy;
    public final double hw;
    public final double gw;

    public MoveComparator(double hw, double gw, HeuristicStrategy strategy) {
      this.hw = hw;
      this.gw = gw;
      this.strategy = strategy;
    }

    @Override
    public int compare(Move m1, Move m2) {
      double h1 = 0, h2 = 0;
      switch (strategy) {
        case HammingDistance:
          h1 = m1.board.hamming();
          h2 = m2.board.hamming();
          break;
        case ManhattanDistance:
          h1 = m1.board.manhattan();
          h2 = m2.board.manhattan();
          break;
        default:
          h1 = m1.board.manhattan();
          h2 = m2.board.manhattan();
      }

      double g1 = m1.numMoves, g2 = m2.numMoves;

      return Double.compare((hw * h1) + (gw * g1), (hw * h2) + (gw * g2));
    }
  }

  private Move lastMove;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    // To get Dijkstra set heuristic weight = 0 (very long running time)
    Comparator<Move> comparator = new MoveComparator(1, 1, MoveComparator.HeuristicStrategy.ManhattanDistance);

    MinPQ<Move> moves = new MinPQ<Move>(comparator);
    moves.insert(new Move(initial));

    MinPQ<Move> twinMoves = new MinPQ<Move>(comparator);
    twinMoves.insert(new Move(initial.twin()));

    while (true) {
      Move move = moves.delMin();
      if (move.board.isGoal()) {
        lastMove = move;
        return;
      }

      Move twinMove = twinMoves.delMin();
      if (twinMove.board.isGoal()) {
        lastMove = null;
        return;
      }

      move.nextMoves().forEach((m) -> moves.insert(m));
      twinMove.nextMoves().forEach((m) -> twinMoves.insert(m));
    }
  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return lastMove != null;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return lastMove != null ? lastMove.numMoves : -1;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (!isSolvable()) return null;

    List<Board> solution = new ArrayList<Board>();
    Move it = lastMove;
    while (it.previous != null) {
      solution.add(it.previous.board);
      it = it.previous;
    }
    Collections.reverse(solution);

    return solution;
  }

  // test client (see below)
  public static void main(String[] args) {
  }
}
