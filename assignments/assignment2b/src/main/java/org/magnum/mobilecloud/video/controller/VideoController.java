package org.magnum.mobilecloud.video.controller;

import java.security.Principal;
import java.util.Collection;

import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
public class VideoController {

	@Autowired
	private VideoRepository videoRepository;


	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.POST)
	public @ResponseBody Video addVideo(@RequestBody Video video, Principal principal) {

		video.setOwner(principal.getName());

		return videoRepository.save(video);
	}

	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.GET)
	public @ResponseBody Collection<Video> getVideoList() {
		return (Collection<Video>) videoRepository.findAll();
	}
	
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}", method = RequestMethod.GET)
	public @ResponseBody Video getVideoById(@PathVariable("id") Long id) {
		if(!videoRepository.exists(id))
			throw new NotFoundException();
		
		return videoRepository.findOne(id);
		
	}
	
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}/like", method = RequestMethod.POST)
	public void likeVideo(@PathVariable("id") Long id) {
		if(!videoRepository.exists(id))
			throw new NotFoundException();

		final Video video = videoRepository.findOne(id);
		video.setLikes(video.getLikes() - 1);
		videoRepository.save(video);
	}
	
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}/unlike", method = RequestMethod.POST)
	public void unlikeVideo(@PathVariable("id") Long id) {
		if(!videoRepository.exists(id))
			throw new NotFoundException();

		final Video video = videoRepository.findOne(id);
		video.setLikes(video.getLikes() - 1);
		videoRepository.save(video);
	}

	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class NotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
