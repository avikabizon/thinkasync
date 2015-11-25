package com.hpe.apppulse.thinkasync;

import android.content.Context;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by selao on 11/25/2015.
 */
public class RawDataReader {
    private Context context;


    public RawDataReader(Context context) {
        this.context = context;
    }


    public String loadJsonStringBlocking(int rawId) throws IOException {
        InputStream is = context.getResources().openRawResource(rawId);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        int n;
        while ((n = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, n);
        }
        is.close();
        return writer.toString();
    }


    public byte[] loadBytesBlocking(int rawId) throws IOException {
        final InputStream inputStream = context.getResources().openRawResource(rawId);
        final byte[] bytes = convertStreamToByteArray(inputStream);
        inputStream.close();
        return bytes;
    }


    private byte[] convertStreamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[10240];
        int i = Integer.MAX_VALUE;
        while ((i = is.read(buff, 0, buff.length)) > 0) {
            baos.write(buff, 0, i);
        }


        return baos.toByteArray();
    } }
