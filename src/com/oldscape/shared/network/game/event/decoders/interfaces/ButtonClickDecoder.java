/**
 * Copyright (c) 2015 Kyle Friz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.oldscape.shared.network.game.event.decoders.interfaces;

import com.oldscape.shared.network.game.DataType;
import com.oldscape.shared.network.game.GameFrameReader;
import com.oldscape.shared.network.game.event.GameMessageDecoder;
import com.oldscape.shared.network.game.event.impl.ButtonClickEvent;

/**
 * @author Kyle Friz
 * @date May 4, 2015
 */
public class ButtonClickDecoder implements GameMessageDecoder<ButtonClickEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.oldscape.shared.network.game.event.GameMessageDecoder#decode(com.oldscape.shared.network.game.GameFrameReader)
	 */
	@Override
	public ButtonClickEvent decode(GameFrameReader frame) {
		int hash = (int) frame.getUnsigned(DataType.INT);
		int widgetID = (int) frame.getUnsigned(DataType.SHORT);
		int widgetChildID = (int) frame.getUnsigned(DataType.SHORT);

		System.out.println("Button click "+(hash >> 16)+" "+(hash & 0xFFFF)+" "+widgetID+" "+widgetChildID);
		return new ButtonClickEvent(frame.getOpcode(), (hash >> 16), (hash & 0xFFFF));
	}

}
