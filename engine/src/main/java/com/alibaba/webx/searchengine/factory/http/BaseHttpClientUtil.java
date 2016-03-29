package com.alibaba.webx.searchengine.factory.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BaseHttpClientUtil {
	
	public final static String CHARACTER	 = "UTF-8";

	/**
     * 通用的流解析方法，返回byte[]
     * @param instream		需解析的流
     * @throws IOException
     * @return byte[]		
     */
    public byte[] getBytesFromInpuStream(InputStream instream) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        try {
            int length;
            byte[] tmp = new byte[8096];
            while ((length = instream.read(tmp)) != -1) {
                outstream.write(tmp, 0, length);
            }
            return outstream.toByteArray();
        } finally {
            instream.close();
            outstream.close();
        }
    }
}
