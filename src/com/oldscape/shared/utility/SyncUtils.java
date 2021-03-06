package com.oldscape.shared.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oldscape.server.game.model.sync.block.encode.AnimationBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.AppearanceBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.ChatBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.ForceChatBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.ForceMovementBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.GraphicBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.HitUpdateBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.InteractingMobBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.MovementTypeBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.OrientationBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.SecondaryHitUpdateBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.SynchronizationBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.TemporaryMovementTypeBlockEncoder;
import com.oldscape.server.game.model.sync.block.encode.TransformBlockEncoder;
import com.oldscape.server.game.model.sync.descriptor.CycleEndDescriptor;
import com.oldscape.server.game.model.sync.descriptor.CycleStartDescriptor;
import com.oldscape.server.game.model.sync.descriptor.NpcAdditionDescriptor;
import com.oldscape.server.game.model.sync.descriptor.NpcRemovalDescriptor;
import com.oldscape.server.game.model.sync.descriptor.NpcRunDescriptor;
import com.oldscape.server.game.model.sync.descriptor.NpcStillDescriptor;
import com.oldscape.server.game.model.sync.descriptor.NpcWalkDescriptor;
import com.oldscape.server.game.model.sync.descriptor.PlayerAdditionDescriptor;
import com.oldscape.server.game.model.sync.descriptor.PlayerRemovalDescriptor;
import com.oldscape.server.game.model.sync.descriptor.PlayerRunDescriptor;
import com.oldscape.server.game.model.sync.descriptor.PlayerSkipDescriptor;
import com.oldscape.server.game.model.sync.descriptor.PlayerStillDescriptor;
import com.oldscape.server.game.model.sync.descriptor.PlayerTeleportDescriptor;
import com.oldscape.server.game.model.sync.descriptor.PlayerWalkDescriptor;
import com.oldscape.server.game.model.sync.descriptor.SynchronizationDescriptor;
import com.oldscape.server.game.model.sync.segment.SegmentType;

public class SyncUtils {

	private static final Map<SegmentType, SynchronizationDescriptor> pDescriptors = new HashMap<>();
	private static final Map<SegmentType, SynchronizationDescriptor> nDescriptors = new HashMap<>();
	private static final List<SynchronizationBlockEncoder> pBlocks = new ArrayList<>(12);
	private static final List<SynchronizationBlockEncoder> nBlocks = new ArrayList<>(8);

	private static final byte[] DIRECTION_DELTA_X = new byte[] { -1, 0, 1, -1, 1, -1, 0, 1 };
	private static final byte[] DIRECTION_DELTA_Y = new byte[] { -1, -1, -1, 0, 0, 1, 1, 1 };

	static {
		pDescriptors.put(SegmentType.CYCLE_START, new CycleStartDescriptor());
		pDescriptors.put(SegmentType.CYCLE_END, new CycleEndDescriptor());
		pDescriptors.put(SegmentType.SKIP, new PlayerSkipDescriptor());
		pDescriptors.put(SegmentType.ADD_MOB, new PlayerAdditionDescriptor());
		pDescriptors.put(SegmentType.REMOVE_MOB, new PlayerRemovalDescriptor());
		pDescriptors.put(SegmentType.TELEPORT, new PlayerTeleportDescriptor());
		pDescriptors.put(SegmentType.WALK, new PlayerWalkDescriptor());
		pDescriptors.put(SegmentType.RUN, new PlayerRunDescriptor());
		pDescriptors.put(SegmentType.NO_MOVEMENT, new PlayerStillDescriptor());

		nDescriptors.put(SegmentType.ADD_MOB, new NpcAdditionDescriptor());
		nDescriptors.put(SegmentType.REMOVE_MOB, new NpcRemovalDescriptor());
		nDescriptors.put(SegmentType.WALK, new NpcWalkDescriptor());
		nDescriptors.put(SegmentType.RUN, new NpcRunDescriptor());
		nDescriptors.put(SegmentType.NO_MOVEMENT, new NpcStillDescriptor());

		pBlocks.add(new AppearanceBlockEncoder());
		pBlocks.add(new AnimationBlockEncoder());
		pBlocks.add(new InteractingMobBlockEncoder());
		pBlocks.add(new ForceChatBlockEncoder());
		pBlocks.add(new HitUpdateBlockEncoder());
		pBlocks.add(new OrientationBlockEncoder());
		pBlocks.add(new ChatBlockEncoder());
		pBlocks.add(new GraphicBlockEncoder());
		pBlocks.add(new ForceMovementBlockEncoder());
		pBlocks.add(new SecondaryHitUpdateBlockEncoder());
		pBlocks.add(new MovementTypeBlockEncoder());
		pBlocks.add(new TemporaryMovementTypeBlockEncoder());

		nBlocks.add(new HitUpdateBlockEncoder());
		nBlocks.add(new InteractingMobBlockEncoder());
		nBlocks.add(new ForceChatBlockEncoder());
		nBlocks.add(new OrientationBlockEncoder());
		nBlocks.add(new GraphicBlockEncoder());
		nBlocks.add(new SecondaryHitUpdateBlockEncoder());
		nBlocks.add(new AnimationBlockEncoder());
		nBlocks.add(new TransformBlockEncoder());
	}

	public static SynchronizationDescriptor getPlayerDescriptor(SegmentType type) {
		return pDescriptors.get(type);
	}

	public static SynchronizationDescriptor getNpcDescriptor(SegmentType type) {
		return nDescriptors.get(type);
	}

	public static SynchronizationBlockEncoder getPlayerBlock(int index) {
		return pBlocks.get(index);
	}

	public static SynchronizationBlockEncoder getNpcBlock(int index) {
		return nBlocks.get(index);
	}

	public static int getDirX(int index) {
		return DIRECTION_DELTA_X[index];
	}

	public static int getDirX(int first, int second) {
		return DIRECTION_DELTA_X[first] + DIRECTION_DELTA_X[second];
	}

	public static int getDirY(int index) {
		return DIRECTION_DELTA_Y[index];
	}

	public static int getDirY(int first, int second) {
		return DIRECTION_DELTA_Y[first] + DIRECTION_DELTA_Y[second];
	}

	public static int getPlayerWalkingDirection(int dirX, int dirY) {
		if (dirX == -1 && dirY == -1) {
			return 0;
		}
		if (dirX == 0 && dirY == -1) {
			return 1;
		}
		if (dirX == 1 && dirY == -1) {
			return 2;
		}
		if (dirX == -1 && dirY == 0) {
			return 3;
		}
		if (dirX == 1 && dirY == 0) {
			return 4;
		}
		if (dirX == -1 && dirY == 1) {
			return 5;
		}
		if (dirX == 0 && dirY == 1) {
			return 6;
		}
		if (dirX == 1 && dirY == 1) {
			return 7;
		}
		return -1;
	}

	public static int getPlayerRunningDirection(int dirX, int dirY) {
		if (dirX == -2 && dirY == -2)
			return 0;

		if (dirX == -1 && dirY == -2)
			return 1;

		if (dirX == 0 && dirY == -2)
			return 2;

		if (dirX == 1 && dirY == -2)
			return 3;

		if (dirX == 2 && dirY == -2)
			return 4;

		if (dirX == -2 && dirY == -1)
			return 5;

		if (dirX == 2 && dirY == -1)
			return 6;

		if (dirX == -2 && dirY == 0)
			return 7;

		if (dirX == 2 && dirY == 0)
			return 8;

		if (dirX == -2 && dirY == 1)
			return 9;

		if (dirX == 2 && dirY == 1)
			return 10;

		if (dirX == -2 && dirY == 2)
			return 11;

		if (dirX == -1 && dirY == 2)
			return 12;

		if (dirX == 0 && dirY == 2)
			return 13;

		if (dirX == 1 && dirY == 2)
			return 14;

		if (dirX == 2 && dirY == 2)
			return 15;

		return -1;
	}
}
