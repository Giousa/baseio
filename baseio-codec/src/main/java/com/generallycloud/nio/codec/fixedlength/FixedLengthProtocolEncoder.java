/*
 * Copyright 2015 GenerallyCloud.com
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.generallycloud.nio.codec.fixedlength;

import java.io.IOException;
import java.nio.charset.Charset;

import com.generallycloud.nio.buffer.ByteBuf;
import com.generallycloud.nio.buffer.ByteBufAllocator;
import com.generallycloud.nio.codec.fixedlength.future.FixedLengthReadFuture;
import com.generallycloud.nio.common.StringUtil;
import com.generallycloud.nio.protocol.ChannelReadFuture;
import com.generallycloud.nio.protocol.ChannelWriteFuture;
import com.generallycloud.nio.protocol.ChannelWriteFutureImpl;
import com.generallycloud.nio.protocol.ProtocolEncoder;

public class FixedLengthProtocolEncoder implements ProtocolEncoder {

	@Override
	public ChannelWriteFuture encode(ByteBufAllocator allocator, ChannelReadFuture future) throws IOException {
		
		if (future.isHeartbeat()) {

			int value = future.isPING() ? FixedLengthProtocolDecoder.PROTOCOL_PING
					: FixedLengthProtocolDecoder.PROTOCOL_PONG;

			ByteBuf buffer = allocator.allocate(4);

			buffer.putInt(value);

			return new ChannelWriteFutureImpl(future, buffer.flip());
		}
		
		Charset charset = future.getContext().getEncoding();
		
		FixedLengthReadFuture f = (FixedLengthReadFuture) future;

		String write_text = f.getWriteText();
		
		if (StringUtil.isNullOrBlank(write_text)) {
			throw new IOException("null write text");
		}
		
		byte [] text_array = write_text.getBytes(charset);

		int size = text_array.length;

		ByteBuf buf = allocator.allocate(size + 4);

		buf.putInt(size);

		buf.put(text_array, 0, size);

		return new ChannelWriteFutureImpl(future, buf.flip());
	}
}
