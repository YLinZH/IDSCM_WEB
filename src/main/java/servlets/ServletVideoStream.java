package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/ServletVideoStream")
public class ServletVideoStream extends HttpServlet {
    
    private static final int BUFFER_SIZE = 1024 * 16; // 16KB de buffer
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getParameter("path");
        if (path == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro id");
            return;
        }

        // Recupera el objeto video (adaptar según tu lógica y capa de acceso a datos)
        File videoFile = new File(path);
        if (!videoFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fichero de vídeo no encontrado");
            return;
        }
        
        long fileLength = videoFile.length();
        long start = 0, end = fileLength - 1;
        boolean isPartialRequest = false;

        // Verificar si el cliente solicita un rango específico
        String rangeHeader = request.getHeader("Range");
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            isPartialRequest = true;
            String[] ranges = rangeHeader.substring(6).split("-");
            try {
                start = Long.parseLong(ranges[0]);
                if (ranges.length > 1) {
                    end = Long.parseLong(ranges[1]);
                }
            } catch (NumberFormatException ignored) {
            }
        }

        long contentLength = end - start + 1;
        response.setContentType(getMimeType(path));
        response.setHeader("Accept-Ranges", "bytes");

        if (isPartialRequest) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206 Partial Content
            response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        response.setHeader("Content-Length", String.valueOf(contentLength));

        // Enviar la parte del archivo solicitada
        try (RandomAccessFile raf = new RandomAccessFile(videoFile, "r");
             OutputStream out = response.getOutputStream()) {
            
            raf.seek(start);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            long bytesRemaining = contentLength;

            while ((bytesRead = raf.read(buffer)) != -1 && bytesRemaining > 0) {
                out.write(buffer, 0, Math.min(bytesRead, (int) bytesRemaining));
                bytesRemaining -= bytesRead;
            }
        }
       
    }
    
    
    private String getMimeType(String filename) {
        if (filename.endsWith(".mp4")) {
            return "video/mp4";
        } else if (filename.endsWith(".webm")) {
            return "video/webm";
        } else if (filename.endsWith(".mkv")) {
            return "video/x-matroska";
        } else {
            return "application/octet-stream";
        }
    }
}
