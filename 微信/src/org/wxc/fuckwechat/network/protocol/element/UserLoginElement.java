package org.wxc.fuckwechat.network.protocol.element;

import java.io.IOException;

import org.wxc.fuckwechat.network.protocol.Element;
import org.wxc.fuckwechat.network.protocol.Leaf;
import org.xmlpull.v1.XmlSerializer;

public class UserLoginElement extends Element {
	private Leaf userid = new Leaf("userid");
	private Leaf password = new Leaf("password");

	@Override
	public void serializeElement(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "element");
			getUserid().serializeLeaf(serializer);
			getPassword().serializeLeaf(serializer);
			serializer.endTag(null, "element");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		return "2002";
	}

	public Leaf getPassword() {
		return password;
	}

	public Leaf getUserid() {
		return userid;
	}

}
