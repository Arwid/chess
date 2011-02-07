/*
 * Created on May 15, 2005
 *
 */
package chess.properties;

/**
 * @author Arvydas Bancewicz
 *
 */
public class Utilities {
    
  public static BoardParameters copyParam(BoardParameters param) {
    BoardParameters newParam = new BoardParameters();
    newParam.setAllowCursorChange(param.allowCursorChange());
    newParam.setPrimaryCell(param.getPrimaryCell());
    newParam.setAlternateCell(param.getAlternateCell());
    newParam.setPieceSet(param.getPieceSet());
    newParam.setShowActiveSpots(param.showActiveSpots());
    newParam.setShowArrows(param.showArrows());
    newParam.setShowMouseOver(param.showMouseOver());
    newParam.setShowLegend(param.showLegend());
    return newParam;
  }
  
}