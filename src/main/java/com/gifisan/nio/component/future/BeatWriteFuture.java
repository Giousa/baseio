package com.gifisan.nio.component.future;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.gifisan.nio.common.Logger;
import com.gifisan.nio.common.LoggerFactory;
import com.gifisan.nio.component.TCPEndPoint;

public class BeatWriteFuture extends AbstractWriteFuture implements WriteFuture {

	private Logger logger = LoggerFactory.getLogger(BeatWriteFuture.class);
	
	public BeatWriteFuture(TCPEndPoint endPoint) {
		super(endPoint, 0, null, null, null);
	}

	private ByteBuffer	beat	= ByteBuffer.wrap(new byte[] { 3 });

	public boolean write() throws IOException {
		beat.position(0);
		attackNetwork(endPoint.write(beat));
		return !beat.hasRemaining();
	}
	
	public void onException(IOException e) {
		logger.error(e.getMessage(),e);
	}

	public void onSuccess() {
	}
}
