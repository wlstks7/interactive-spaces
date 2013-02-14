/*
 * Copyright (C) 2012 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package interactivespaces.service.audio.player;

import java.io.File;

/**
 * A playable track. Includes meta data and the location of the song in the
 * filesystem.
 * 
 * @author Keith M. Hughes
 */
public class PlayableAudioTrack {
	/**
	 * The metadata about the track.
	 */
	private AudioTrack track;

	/**
	 * File containing the track
	 */
	private File file;

	/**
	 * 
	 * @param track
	 *            The metadata about the track.
	 * @param file
	 *            File containing the track
	 */
	public PlayableAudioTrack(AudioTrack track, File file) {
		this.track = track;
		this.file = file;
	}

	/**
	 * @return the track's metadata.
	 */
	public AudioTrack getTrack() {
		return track;
	}

	/**
	 * @return the file containing the track
	 */
	public File getFile() {
		return file;
	}

	@Override
	public String toString() {
		return "PlayableAudioTrack [track=" + track + ", file=" + file + "]";
	}
}
