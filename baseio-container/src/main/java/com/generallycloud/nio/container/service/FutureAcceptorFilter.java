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
package com.generallycloud.nio.container.service;

import com.generallycloud.nio.component.IoEventHandle;
import com.generallycloud.nio.component.SocketSession;
import com.generallycloud.nio.container.ApplicationContext;
import com.generallycloud.nio.container.Initializeable;
import com.generallycloud.nio.container.InitializeableImpl;
import com.generallycloud.nio.container.configuration.Configuration;
import com.generallycloud.nio.protocol.NamedReadFuture;
import com.generallycloud.nio.protocol.ReadFuture;

public abstract class FutureAcceptorFilter extends InitializeableImpl implements Initializeable, IoEventHandle {

	private int sortIndex;

	@Override
	public void initialize(ApplicationContext context, Configuration config) throws Exception {

	}

	@Override
	public void accept(SocketSession session, ReadFuture future) throws Exception {
		this.accept(session, (NamedReadFuture) future);
	}

	protected abstract void accept(SocketSession session, NamedReadFuture future) throws Exception;

	@Override
	public void exceptionCaught(SocketSession session, ReadFuture future, Exception cause, IoEventState state) {

	}

	@Override
	public void futureSent(SocketSession session, ReadFuture future) {

	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

}
