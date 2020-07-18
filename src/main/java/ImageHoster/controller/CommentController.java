package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;


@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value ="/image/{id}/{title}/comments",method = RequestMethod.POST)
    public String addImageComment(@PathVariable(name = "id")Integer id, @PathVariable(name="title")String title, @RequestParam("comment") String comment, HttpSession session) throws
            IOException {
        Comment imageComment = new Comment();
        imageComment.setText(comment);
        imageComment.setCreatedDate(LocalDate.now());
        Image image = imageService.getImage(id);
        imageComment.setImage(image);
        imageComment.setUser((User)session.getAttribute("loggeduser"));
        commentService.addComment(imageComment);
        return "redirect:/images/"+id+"/"+title;
    }
}
