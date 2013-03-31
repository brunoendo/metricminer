package org.metricminer.worker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.metricminer.worker.http.Response;
import org.metricminer.worker.representations.TaskRepresentation;

import br.com.caelum.vraptor.ioc.Component;

import com.thoughtworks.xstream.XStream;

@Component
public class Worker {
	
	private DefaultHttpClient defaultHttpClient;

	public Response sendTask(TaskRepresentation taskRepresentation) {
        String xml = new XStream().toXML(taskRepresentation);
        defaultHttpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://localhost:8888/jobs/queue");
		try {
			return new Response(send(xml, post));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private HttpResponse send(String xml, HttpPost post) throws UnsupportedEncodingException, IOException,
			ClientProtocolException {
		HttpEntity entity = new StringEntity(xml);
        post.setEntity(entity);
        post.setHeader("Content-Type", "application/xml");
        return defaultHttpClient.execute(post);
	}
	
}
