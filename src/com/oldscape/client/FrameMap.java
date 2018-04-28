package com.oldscape.client;

public class FrameMap extends Node {

	static Buffer NetCache_reference;

	int id;

	int count;

	int[] types;

	int[][] list;

	FrameMap(int var1, byte[] var2) {
		this.id = var1;
		Buffer var3 = new Buffer(var2);
		this.count = var3.readUnsignedByte();
		this.types = new int[this.count];
		this.list = new int[this.count][];

		int var4;
		for (var4 = 0; var4 < this.count; ++var4) {
			this.types[var4] = var3.readUnsignedByte();
		}

		for (var4 = 0; var4 < this.count; ++var4) {
			this.list[var4] = new int[var3.readUnsignedByte()];
		}

		for (var4 = 0; var4 < this.count; ++var4) {
			for (int var5 = 0; var5 < this.list[var4].length; ++var5) {
				this.list[var4][var5] = var3.readUnsignedByte();
			}
		}

	}

	static final void worldToMinimap(int var0, int var1, int var2, int var3, SpritePixels var4, class236 var5) {
		int var6 = var3 * var3 + var2 * var2;
		if (var6 > 4225 && var6 < 90000) {
			int var7 = Client.mapAngle & 2047;
			int var8 = Graphics3D.SINE[var7];
			int var9 = Graphics3D.COSINE[var7];
			int var10 = var9 * var2 + var3 * var8 >> 16;
			int var11 = var3 * var9 - var8 * var2 >> 16;
			double var12 = Math.atan2(var10, var11);
			int var14 = var5.field2773 / 2 - 25;
			int var15 = (int) (Math.sin(var12) * var14);
			int var16 = (int) (Math.cos(var12) * var14);
			byte var17 = 20;
			Client.mapedge.method5876(var15 + (var0 + var5.field2773 / 2 - var17 / 2),
					var5.field2772 / 2 + var1 - var17 / 2 - var16 - 10, var17, var17, 15, 15, var12, 256);
		} else {
			class38.drawDot(var0, var1, var2, var3, var4, var5);
		}

	}
}
