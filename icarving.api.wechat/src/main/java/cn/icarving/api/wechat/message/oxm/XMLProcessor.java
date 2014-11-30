package cn.icarving.api.wechat.message.oxm;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLProcessor<T> {

	private XStream xstream = new XStream(new DomDriver());

	public XMLProcessor(Class<T> clazz) {
		xstream.processAnnotations(clazz);
	}

	public String objectToXML(Object obj) {
		return xstream.toXML(obj);
	}

	@SuppressWarnings("unchecked")
	public T xmlToObject(String xml) {
		return (T) xstream.fromXML(xml);
	}

}
