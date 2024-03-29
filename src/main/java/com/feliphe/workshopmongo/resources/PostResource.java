package com.feliphe.workshopmongo.resources;

import static com.feliphe.workshopmongo.resources.util.URL.convertDate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feliphe.workshopmongo.domain.Post;
import com.feliphe.workshopmongo.resources.util.URL;
import com.feliphe.workshopmongo.services.PostService;

@RestController
@RequestMapping(value="/posts")
public class PostResource {

	@Autowired
	private PostService postService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Post> findById(@PathVariable String id) {
		
		Post post = postService.findById(id);
		
		return ResponseEntity.ok().body(post);
	}
	
	@RequestMapping(value="/titlesearch", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value="text", defaultValue="") String text) {
		
		List<Post> post = postService.findByTitle(URL.decodeParam(text));
		
		return ResponseEntity.ok().body(post);
	}
	
	@RequestMapping(value="/fullsearch", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> fullSearch(
			@RequestParam(value="text", defaultValue="") String text,
			@RequestParam(value="minDate", defaultValue="") String minDate,
			@RequestParam(value="maxDate", defaultValue="") String maxDate) {
		
		Date min = convertDate(minDate, new Date(0L));
		Date max = convertDate(maxDate, new Date());
		
		List<Post> post = postService.fullSearch(URL.decodeParam(text), min, max);
		
		return ResponseEntity.ok().body(post);
	}
	
}
