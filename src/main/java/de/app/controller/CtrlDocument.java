package de.app.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import de.app.RestRequest;


@RestController
@RequestMapping(value="/api/documents")
public class CtrlDocument {
	
	@Autowired
	RestRequest rest;
	
	@RequestMapping( method=RequestMethod.GET  )
	public ResponseEntity<?> get(){
		return null;
	}
	

	@RequestMapping(value="/addDocument", method=RequestMethod.POST  )
	public @ResponseBody String addDocument(  MultipartHttpServletRequest mRequest, HttpServletResponse response ){

	     try {
	            mRequest.getParameterMap();
	            Iterator<String> itr = mRequest.getFileNames();
	            while (itr.hasNext()) {
	                MultipartFile mFile = mRequest.getFile(itr.next());
	                this.upload(mFile);
	                String filename = mFile.getOriginalFilename();
	                MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
	                parts.add("file", mFile);

	                this.writeFile(mFile);
	            }
	            return  new String("succest");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new String("defait");
	        }

	}

	public void fetchDocument( InputStream in , MultipartHttpServletRequest mRequest ) throws RestClientException, Exception{
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		    HttpEntity<String> entity = new HttpEntity<String>(headers);

		    ResponseEntity<byte[]> response = rest.getObject().exchange(
		            "https://www.google.com/images/srpr/logo11w.png",
		            HttpMethod.GET, entity, byte[].class, "1");

		    if (response.getStatusCode() == HttpStatus.OK) {
		        Files.write(Paths.get("google.png"), response.getBody());
		    }
	
	}

	public void upload( MultipartFile file) throws RestClientException, Exception{
		  MultiValueMap<String, Object> parts = 
		          new LinkedMultiValueMap<String, Object>();
		  parts.add("file", new ByteArrayResource(file.getBytes()));
		  parts.add("filename", file.getOriginalFilename());
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		  HttpEntity<MultiValueMap<String, Object>> requestEntity =
		          new HttpEntity<MultiValueMap<String, Object>>(parts, headers);

	         rest.getObject().exchange("http://localhost:8080/api/documents/addDocument", 
	                  HttpMethod.POST, requestEntity, String.class);
	}

	@RequestMapping(value="/{documentId}", method=RequestMethod.GET  )
	public ResponseEntity<?> documentId( @PathVariable(value="documentId") Long documentId ){
		return null;
	}

	@RequestMapping(value="/{documentId}/changeOwner", method=RequestMethod.POST  )
	public ResponseEntity<?> documentId_changeOwner( @PathVariable(value="documentId") Long documentId ){
		return null;
	}

	@RequestMapping(value="/{documentId}/shareDocument", method=RequestMethod.POST  )
	public ResponseEntity<?> documentId_shareDocument( @PathVariable(value="documentId") Long documentId ){
		return null;
	}

	private String writeFile( MultipartFile file ){
	    String name = file.getOriginalFilename();
	    System.out.println("come here");
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }	
	}

}
