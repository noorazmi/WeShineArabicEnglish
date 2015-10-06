package com.moderneng.listeners;

/**
 * Listener interface to listen the game end event
 * @author noor.alam
 *
 */
public interface OnGameEndListener {
	/**
	 * Will be called when the user stops drawing path.
	 * @param isSuccessful true if the user drew a right path
	 */
	void onGameEnd(boolean isSuccessful);
}
