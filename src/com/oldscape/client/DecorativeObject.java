package com.oldscape.client;

public final class DecorativeObject {

	int floor;

	int x;

	int y;

	int renderFlag;

	int rotation;

	int offsetX;

	int offsetY;

	public Renderable renderable1;

	public Renderable renderable2;

	public int hash;

	int renderInfoBitPacked;

	DecorativeObject() {
		this.hash = 0;
		this.renderInfoBitPacked = 0;
	}

	static final void method3082(long var0) {
		try {
			Thread.sleep(var0);
		} catch (InterruptedException var3) {
			;
		}

	}

	public static void method3081() {
		KeyFocusListener var0 = KeyFocusListener.keyboard;
		synchronized (KeyFocusListener.keyboard) {
			++KeyFocusListener.keyboardIdleTicks;
			KeyFocusListener.field620 = KeyFocusListener.field623;
			KeyFocusListener.field638 = 0;
			int var1;
			if (KeyFocusListener.field627 < 0) {
				for (var1 = 0; var1 < 112; ++var1) {
					KeyFocusListener.keyPressed[var1] = false;
				}

				KeyFocusListener.field627 = KeyFocusListener.field626;
			} else {
				while (KeyFocusListener.field627 != KeyFocusListener.field626) {
					var1 = KeyFocusListener.field625[KeyFocusListener.field626];
					KeyFocusListener.field626 = KeyFocusListener.field626 + 1 & 127;
					if (var1 < 0) {
						KeyFocusListener.keyPressed[~var1] = false;
					} else {
						if (!KeyFocusListener.keyPressed[var1]
								&& KeyFocusListener.field638 < KeyFocusListener.field630.length - 1) {
							KeyFocusListener.field630[++KeyFocusListener.field638 - 1] = var1;
						}

						KeyFocusListener.keyPressed[var1] = true;
					}
				}
			}

			if (KeyFocusListener.field638 > 0) {
				KeyFocusListener.keyboardIdleTicks = 0;
			}

			KeyFocusListener.field623 = KeyFocusListener.field631;
		}
	}
}
