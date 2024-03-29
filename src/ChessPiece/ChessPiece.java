package ChessPiece;

import Drawing.DescriptionPane;
import GameLogic.GameLogic;
import GameLogic.GameUtil;
import javafx.scene.paint.Color;
import sharedObject.RenderableHolder;

import java.util.ArrayList;

public abstract class ChessPiece implements Movable {
    private int posX;
    private int posY;
    private double rate;
    private String pieceUrl;
    private boolean whiteTeam; //white is true black is false
    protected ArrayList<ChessPosition> possibleMoves;

    public ChessPiece(int x, int y, boolean t) {
        setTeam(t);
        posX = x;
        posY = y;
    }


    public boolean isWhite() {
        return whiteTeam;
    }


    public void setTeam(boolean team) {
        this.whiteTeam = team;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getPieceType() {
        if (this instanceof Pawn) {
            return "Pawn";
        } else if (this instanceof Knight) {
            return "Knight";
        } else if (this instanceof Bishop) {
            return "Bishop";
        } else if (this instanceof Rook) {
            return "Rook";
        } else if (this instanceof Queen) {
            return "Queen";
        } else if (this instanceof King) {
            return "King";
        }
        return "";
    }


    public void setPieceUrl(String pieceUrl) {
        this.pieceUrl = pieceUrl;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return posY;
    }

    public String toString() {
        if (isWhite()) {
            if (this instanceof Pawn) {
                return "White Pawn ";
            } else if (this instanceof Knight) {
                return "White Knight ";
            } else if (this instanceof Rook) {
                return "White Rook ";
            } else if (this instanceof Queen) {
                return "White Queen ";
            } else if (this instanceof King) {
                return "White King ";
            } else if (this instanceof Bishop) {
                return "White Bishop ";
            }
        } else {
            if (this instanceof Pawn) {
                return "Black Pawn ";
            } else if (this instanceof Knight) {
                return "Black Knight ";
            } else if (this instanceof Rook) {
                return "Black Rook ";
            } else if (this instanceof Queen) {
                return "Black Queen ";
            } else if (this instanceof King) {
                return "Black King ";
            } else if (this instanceof Bishop) {
                return "Black Bishop ";
            }
        }
        return "Invalid";
    }


    public abstract void setCurrentAllPossibleMoves();

    public boolean move(int x, int y) {
        ChessPiece current = GameLogic.getInstance().getCurrentClickingPiece();
        if (isValidMove(x, y)) {
            int oldX = getPosX();  // Store the old X position
            int oldY = getPosY();  // Store the old Y position

            if (GameLogic.getInstance().getChessPieceAt(x, y) == null) { //moving normally
                GameLogic.getInstance().setChessPieceAt(getPosX(), getPosY(), null);
                RenderableHolder.moveChess.play();
                DescriptionPane.setColor(Color.BLACK);
                setPosY(y);
                setPosX(x);
                GameLogic.getInstance().setChessPieceAt(getPosX(), getPosY(), this);
                String descriptionText = current + "\nmoved from (" + oldX + "," + oldY + ")\nto (" + getPosX() + "," + getPosY() + ").\n";
                if (this instanceof Pawn) {
                    ChessPosition tmp = new ChessPosition(getPosX(), getPosY());
                    if (isWhite() && getPosY() == 7) {
                        ((Pawn) this).promote();
                        descriptionText += "A white pawn is\npromoted to Queen\nat (" + tmp.getX() + "," + tmp.getY() + ").\n";
                    } else if (!isWhite() && getPosY() == 0) {
                        ((Pawn) this).promote();
                        descriptionText += "A black pawn is\npromoted to Queen\nat (" + tmp.getX() + "," + tmp.getY() + ").\n";
                    }
                }
                GameLogic.getInstance().setCurrentDesc(descriptionText);
            } else {
                // attack
                if (GameUtil.attack(current, GameLogic.getInstance().getChessPieceAt(x, y), x, y)) {
                    // successfully attacked
                    RenderableHolder.captureChess.play();
                    DescriptionPane.setColor(Color.BLACK);
                    GameLogic.getInstance().setChessPieceAt(getPosX(), getPosY(), null);
                    setPosY(y);
                    setPosX(x);
                    String descriptionText = current + "\nfrom (" + oldX + "," + oldY + ")\nKilled an enemy at \n(" + getPosX() + "," + getPosY() + ").\n";
                    if (this instanceof Pawn) {
                        ChessPosition tmp = new ChessPosition(getPosX(), getPosY());
                        if (isWhite() && getPosY() == 7) {
                            ((Pawn) this).promote();
                            descriptionText += "A white pawn is\npromoted to Queen\nat (" + tmp.getX() + "," + tmp.getY() + ").\n";
                        } else if (!isWhite() && getPosY() == 0) {
                            ((Pawn) this).promote();
                            descriptionText += "A black pawn is\npromoted to Queen\nat (" + tmp.getX() + "," + tmp.getY() + ").\n";
                        }
                    }
                    GameLogic.getInstance().setCurrentDesc(descriptionText);
                } else {
                    // failed to attack, killed
                    RenderableHolder.captureFailed.play();
                    DescriptionPane.setColor(Color.RED);
                    GameLogic.getInstance().setChessPieceAt(oldX, oldY, null);
                    setPosX(-10);  // Restore the original X position
                    setPosY(-10);  // Restore the original Y position
                    GameLogic.getInstance().setCurrentDesc(current + "\nfrom (" + oldX + "," + oldY + ")\nFailed to kill,\ngot revenged \nto death!!");
                }
            }
            if (this instanceof Pawn) ((Pawn) this).setNotMoved(false);
            return true;
        }
        return false;
    }

    public ArrayList<ChessPosition> getPossibleMoves() {
        setCurrentAllPossibleMoves();
        return possibleMoves;
    }
}

