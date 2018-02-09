package com.zlt.whiteblackchess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 524202 on 2018/2/9.
 */

public class WhiteBlackView extends View {

    private int OUTLINE_WIDTH = dp2px(5);
    private int INNERLINE_WIDTH = dp2px(2);
    private int MAX_SIZE = 8;
    private int width;
    private Context mContext;
    private Paint linePaint = null;
    private float cellSize;
    private GestureDetectorCompat gestureDetectorCompat;
    private GestureDetector.OnGestureListener gestureListener;
    private boolean isBlackTurn = true;
    private Cell[][] chessArray = new Cell[MAX_SIZE][MAX_SIZE];
    private Paint chessPaint = null;
    private int lastId = 1000;

    public WhiteBlackView(Context context) {
        super(context);
        init(context);
    }

    public WhiteBlackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WhiteBlackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeWidth(INNERLINE_WIDTH);

        chessPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        chessPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        gestureListener = new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (e.getX() <= cellSize / 2 || e.getX() >= (width - cellSize / 2)
                        || e.getY() <= cellSize / 2 || e.getY() >= (width - cellSize / 2)) {
                    //下到棋盘外不处理
                    return false;
                }
                int posX = (int) ((e.getX() - cellSize / 2) / cellSize);
                int posY = (int) ((e.getY() - cellSize / 2) / cellSize);
                Cell cell = chessArray[posX][posY];
                if (cell != null) {
                    return false;
                }

                if (!checkValid(posX, posY)) {
                    Log.d("zlt", "point [" + posX + "," + posY + "] is not valid");
                    return false;
                }

                addAnChess(posX, posY);

                reveralChessBoard(posX, posY);

                invalidate();

                isBlackTurn = !isBlackTurn;

                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        };
        gestureDetectorCompat = new GestureDetectorCompat(mContext, gestureListener);
        initChessArray();
    }

    private void reveralChessBoard(int x, int y) {
        int tempX;
        int tempY;
        boolean isValid = false;
        //1.正北方向查找
        if (y > 0 && chessArray[x][y - 1] != null && chessArray[x][y - 1].isBlack() != isBlackTurn) {
            tempY = y - 1;
            while (tempY > 0) {
                tempY--;
                Cell cell = chessArray[x][tempY];
                if (cell == null) {
                    break;
                }
                if (cell.isBlack() == isBlackTurn) {
                    for (int i = y - 1; i > tempY; i--) {
                        chessArray[x][i].setBlack(isBlackTurn);
                    }
                    break;
                }
            }
        }

        //正南方向查找
        if (y < MAX_SIZE - 1 && chessArray[x][y + 1] != null && chessArray[x][y + 1].isBlack() != isBlackTurn) {
            tempY = y + 1;
            while (tempY < MAX_SIZE - 1) {
                tempY++;
                Cell cell = chessArray[x][tempY];
                if (cell == null) {
                    break;
                }
                if (cell.isBlack() == isBlackTurn) {
                    for (int i = y + 1; i < tempY; i++) {
                        chessArray[x][i].setBlack(isBlackTurn);
                    }
                    break;
                }
            }
        }

        //正东方向查找
        if (x < MAX_SIZE - 1 && chessArray[x + 1][y] != null && chessArray[x + 1][y].isBlack() != isBlackTurn) {
            tempX = x + 1;
            while (tempX < MAX_SIZE - 1) {
                tempX++;
                Cell cell = chessArray[tempX][y];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    for (int i = x + 1; i < tempX; i++) {
                        chessArray[i][y].setBlack(isBlackTurn);
                    }
                    break;
                }
            }
        }

        //正西方向查找
        if (x > 0 && chessArray[x - 1][y] != null && chessArray[x - 1][y].isBlack() != isBlackTurn) {
            tempX = x - 1;
            while (tempX > 0) {
                tempX--;
                Cell cell = chessArray[tempX][y];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    for (int i = x - 1; i > tempX; i--) {
                        chessArray[i][y].setBlack(isBlackTurn);
                    }
                    break;
                }
            }
        }

        //东北方向查找
        if ((y > 0 && x < MAX_SIZE - 1)
                && chessArray[x + 1][y - 1] != null && chessArray[x + 1][y - 1].isBlack() != isBlackTurn) {
            tempX = x + 1;
            tempY = y - 1;
            while (tempY > 0 && tempX < MAX_SIZE - 1) {
                Cell cell = chessArray[tempX++][tempY--];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    for (int i = x + 1, j = y - 1; i < tempX && j > tempY; i++, j--) {
                        chessArray[i][j].setBlack(isBlackTurn);
                    }
                    break;
                }
            }
        }

        //东南方向查找
        if ((y < MAX_SIZE - 1 && x < MAX_SIZE - 1)
                && chessArray[x + 1][y + 1] != null && chessArray[x + 1][y + 1].isBlack() != isBlackTurn) {
            tempX = x + 1;
            tempY = y + 1;
            while (tempY < MAX_SIZE - 1 && tempX < MAX_SIZE - 1) {
                Cell cell = chessArray[tempX++][tempY++];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    for (int i = x + 1, j = y + 1; i < tempX && j < tempY; i++, j++) {
                        chessArray[i][j].setBlack(isBlackTurn);
                    }
                    break;
                }
            }
        }

        //西南方向查找
        if ((y < MAX_SIZE - 1 && x > 0)
                && chessArray[x - 1][y + 1] != null && chessArray[x - 1][y + 1].isBlack() != isBlackTurn) {
            tempX = x - 1;
            tempY = y + 1;
            while (tempY < MAX_SIZE - 1 && tempX > 0) {
                Cell cell = chessArray[tempX--][tempY++];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    for (int i = x - 1, j = y + 1; i > tempX && j < tempY; i--, j++) {
                        chessArray[i][j].setBlack(isBlackTurn);
                    }
                    break;
                }
            }
        }

        //西北方向查找
        if ((y > 0 && x > 0)
                && chessArray[x - 1][y - 1] != null && chessArray[x - 1][y - 1].isBlack() != isBlackTurn) {
            tempX = x - 1;
            tempY = y - 1;
            while (tempY > 0 && tempX > 0) {
                Cell cell = chessArray[tempX--][tempY--];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    for (int i = x - 1, j = y - 1; i > tempX && j > tempY; i--, j--) {
                        chessArray[i][j].setBlack(isBlackTurn);
                    }
                    break;
                }
            }
        }
    }

    private void addAnChess(int posX, int posY) {
        Cell black = new Cell();
        black.setXY(posX, posY);
        if (isBlackTurn) {
            black.setBlack(true);
        } else {
            black.setBlack(false);
        }
        black.setId(++lastId);
        chessArray[posX][posY] = black;
        invalidate();
    }

    private void initChessArray() {
        chessArray[MAX_SIZE / 2 - 1][MAX_SIZE / 2 - 1] = newCell(MAX_SIZE / 2 - 1, MAX_SIZE / 2 - 1, true);
        chessArray[MAX_SIZE / 2 - 1][MAX_SIZE / 2] = newCell(MAX_SIZE / 2 - 1, MAX_SIZE / 2, false);
        chessArray[MAX_SIZE / 2][MAX_SIZE / 2] = newCell(MAX_SIZE / 2, MAX_SIZE / 2, true);
        chessArray[MAX_SIZE / 2][MAX_SIZE / 2 - 1] = newCell(MAX_SIZE / 2, MAX_SIZE / 2 - 1, false);
    }

    private boolean checkValid(int x, int y) {
        int tempX;
        int tempY;
        //1.正北方向查找
        if (y > 0 && chessArray[x][y - 1] != null && chessArray[x][y - 1].isBlack() != isBlackTurn) {
            tempY = y - 1;
            while (tempY > 0) {
                tempY--;
                Cell cell = chessArray[x][tempY];
                if (cell == null) {
                    break;
                }
                if (cell.isBlack() == isBlackTurn) {
                    return true;
                }
            }
        }

        //正南方向查找
        if (y < MAX_SIZE - 1 && chessArray[x][y + 1] != null && chessArray[x][y + 1].isBlack() != isBlackTurn) {
            tempY = y + 1;
            while (tempY < MAX_SIZE - 1) {
                tempY++;
                Cell cell = chessArray[x][tempY];
                if (cell == null) {
                    break;
                }
                if (cell.isBlack() == isBlackTurn) {
                    return true;
                }
            }
        }

        //正东方向查找
        if (x < MAX_SIZE - 1 && chessArray[x + 1][y] != null && chessArray[x + 1][y].isBlack() != isBlackTurn) {
            tempX = x + 1;
            while (tempX < MAX_SIZE - 1) {
                tempX++;
                Cell cell = chessArray[tempX][y];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    return true;
                }
            }
        }

        //正西方向查找
        if (x > 0 && chessArray[x - 1][y] != null && chessArray[x - 1][y].isBlack() != isBlackTurn) {
            tempX = x - 1;
            while (tempX > 0) {
                tempX--;
                Cell cell = chessArray[tempX][y];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    return true;
                }
            }
        }

        //东北方向查找
        if ((y > 0 && x < MAX_SIZE - 1)
                && chessArray[x + 1][y - 1] != null && chessArray[x + 1][y - 1].isBlack() != isBlackTurn) {
            tempX = x + 1;
            tempY = y - 1;
            while (tempY > 0 && tempX < MAX_SIZE - 1) {
                Cell cell = chessArray[tempX++][tempY--];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    return true;
                }
            }
        }

        //东南方向查找
        if ((y < MAX_SIZE - 1 && x < MAX_SIZE - 1)
                && chessArray[x + 1][y + 1] != null && chessArray[x + 1][y + 1].isBlack() != isBlackTurn) {
            tempX = x + 1;
            tempY = y + 1;
            while (tempY < MAX_SIZE - 1 && tempX < MAX_SIZE - 1) {
                Cell cell = chessArray[tempX++][tempY++];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    return true;
                }
            }
        }

        //西南方向查找
        if ((y < MAX_SIZE - 1 && x > 0)
                && chessArray[x - 1][y + 1] != null && chessArray[x - 1][y + 1].isBlack() != isBlackTurn) {
            tempX = x - 1;
            tempY = y + 1;
            while (tempY < MAX_SIZE - 1 && tempX > 0) {
                Cell cell = chessArray[tempX--][tempY++];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    return true;
                }
            }
        }

        //西北方向查找
        if ((y > 0 && x > 0)
                && chessArray[x - 1][y - 1] != null && chessArray[x - 1][y - 1].isBlack() != isBlackTurn) {
            tempX = x - 1;
            tempY = y - 1;
            while (tempY > 0 && tempX > 0) {
                Cell cell = chessArray[tempX--][tempY--];
                if (cell == null) break;
                if (cell.isBlack() == isBlackTurn) {
                    return true;
                }
            }
        }

        return false;
    }

    private Cell newCell(int x, int y, boolean isBlack) {
        Cell cell = new Cell();
        cell.setXY(x, y);
        cell.setBlack(isBlack);
        cell.setId(++lastId);
        return cell;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        cellSize = width / 9.f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOutline(canvas);
        drawChessColor(canvas);
        drawInnerline(canvas);
        drawChess(canvas);
    }

    private void drawChess(Canvas canvas) {
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                if (chessArray[i][j] != null) {
                    Cell cell = chessArray[i][j];
                    chessPaint.setColor(cell.isBlack() ? Color.BLACK : Color.WHITE);
                    canvas.save();
                    canvas.translate(cellSize, cellSize);
                    canvas.drawCircle(i * cellSize, j * cellSize, cellSize / 2 - dp2px(5), chessPaint);
                    if (cell.getId() == lastId) {
                        chessPaint.setColor(Color.RED);
                        canvas.drawCircle(i * cellSize, j * cellSize, dp2px(3), chessPaint);
                    }
                    canvas.restore();
                }
            }
        }
    }

    private void drawChessColor(Canvas canvas) {
        linePaint.setColor(Color.parseColor("#00d09b"));
        canvas.save();
        canvas.translate(cellSize / 2, cellSize / 2);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1) {
                    canvas.save();
                    canvas.translate(i * cellSize, j * cellSize);
                    canvas.drawRect(0, 0, cellSize, cellSize, linePaint);
                    canvas.restore();
                }
            }
        }
        canvas.restore();
        linePaint.setColor(Color.BLACK);
    }

    private void drawInnerline(Canvas canvas) {
        for (int i = 0; i < 9; i++) {
            canvas.save();
            canvas.translate(0, i * cellSize);
            canvas.drawLine(cellSize / 2, cellSize / 2, width - cellSize / 2, cellSize / 2, linePaint);
            canvas.restore();
            canvas.save();
            canvas.translate(i * cellSize, 0);
            canvas.drawLine(cellSize / 2, cellSize / 2, cellSize / 2, width - cellSize / 2, linePaint);
            canvas.restore();
        }
    }

    private void drawOutline(Canvas canvas) {
        canvas.drawRect(0, 0, width, OUTLINE_WIDTH, linePaint);
        canvas.drawRect(0, width - OUTLINE_WIDTH, width, width, linePaint);
        canvas.drawRect(0, 0, OUTLINE_WIDTH, width, linePaint);
        canvas.drawRect(width - OUTLINE_WIDTH, 0, width, width, linePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }

    public int dp2px(int dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
