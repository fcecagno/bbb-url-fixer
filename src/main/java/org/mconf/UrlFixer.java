/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mconf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.codec.digest.DigestUtils;

public class UrlFixer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Server salt: ");
		String salt = in.readLine();
		
		System.out.println("Paste BigBlueButton API calls to fix the checksum (CTRL+C or empty to quit)");
		String s;
		while ((s = in.readLine()) != null && s.length() != 0) {
			try {
				String prefix = s.substring(0, s.indexOf("/api/") + 5);
				String params = s.substring(s.indexOf("?") + 1);
				params = params.replaceAll("&checksum=[^&]*", "");
				String method = s.substring(s.indexOf("/api/") + 5, s.indexOf("?"));
				System.out.println("prefix !" + prefix + "!");
				System.out.println("params !" + params + "!");
				System.out.println("method !" + method + "!");
				System.out.println("signing !" + method + params + salt + "!");
				String checksum = DigestUtils.sha1Hex(method + params + salt);
				String output = prefix + method + "?" + params + "&checksum=" + checksum;
				System.out.println("=> " + output);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
