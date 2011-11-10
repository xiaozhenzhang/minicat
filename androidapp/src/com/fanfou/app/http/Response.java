﻿package com.fanfou.app.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fanfou.app.App;
import com.fanfou.app.api.ApiException;

/**
 * @author mcxiaoke
 * @version 1.0 2011.06.10
 * @version 2.0 2011.09.05
 * @version 3.0 2011.11.10
 * 
 */
public class Response implements ResponseCode {

	// private HttpResponse response;
	private HttpEntity entity;
	private String content;
	private boolean used;
	public final StatusLine statusLine;
	public final int statusCode;

	// public final Header[] headers;

	public Response(HttpResponse response) {
		// this.response = response;
		this.entity = response.getEntity();
		this.statusLine = response.getStatusLine();
		this.statusCode = statusLine.getStatusCode();
		// this.headers = response.getAllHeaders();
	}

	public final String getContent() throws ApiException {
		if (content == null) {
			try {
				content = EntityUtils.toString(entity, HTTP.UTF_8);
				used = true;
			} catch (IOException e) {
				if (App.DEBUG) {
					e.printStackTrace();
				}
				throw new ApiException(ERROR_NOT_CONNECTED, e.getMessage(), e);
			} finally {
			}
		}
		return content;
	}
	
	public final JSONObject getJSONObject() throws ApiException{
		try {
			return new JSONObject(getContent());
		} catch (JSONException e) {
			throw new ApiException(ResponseCode.ERROR_PARSE_FAILED, e);
		}
	}
	
	public final JSONArray getJSONArray() throws ApiException{
		try {
			return new JSONArray(getContent());
		} catch (JSONException e) {
			throw new ApiException(ResponseCode.ERROR_PARSE_FAILED, e);
		}
	}

	@Override
	public String toString() {
		return "HttpResponse{" + "statusCode=" + statusCode + ", content='"
				+ content + '\'' + ", used=" + used + '}';
	}
}