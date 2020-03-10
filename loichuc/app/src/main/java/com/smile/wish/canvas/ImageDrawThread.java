package com.smile.wish.canvas;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ImageDrawThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private Panel _panel;
	private boolean _run = false;

	public ImageDrawThread(SurfaceHolder surfaceHolder, Panel panel) {
		_surfaceHolder = surfaceHolder;
		_panel = panel;
	}

	public SurfaceHolder getSurfaceHolder() {
		return _surfaceHolder;
	}

	public void setRunning(boolean run) {
		_run = run;
	}

	@SuppressLint("WrongCall")
	@Override
	public void run() {
		Canvas canvas;
		while (_run) {
			canvas = null;
			try {
				canvas = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {
					_panel.onDraw(canvas);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (canvas != null) {
					_surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}

		}
	}
}
