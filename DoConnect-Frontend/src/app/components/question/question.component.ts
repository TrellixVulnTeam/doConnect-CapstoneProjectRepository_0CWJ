import { AuthenticationService } from './../../services/authentication.service';
import { QuestionService } from './../../services/question.service';
import { Question } from './../../../assets/class/Question';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {

  constructor(private questionService:QuestionService,private route:ActivatedRoute,private router:Router,private authenticationService:AuthenticationService) { }


  questions!:Question[];
  message:any;
  search:any="";
  displayedColumns: any[] = ['questionId', 'topic', 'question','askedDateAndTime','askedBy', 'answer'];

  ngOnInit(): void {
    this.route.paramMap.subscribe(
      params=>{
        this.search = params.get("search");
       
        if(this.search){
          this.questionService.searchQuestion(this.search).subscribe(
            response=>{
              if(response != null){
                console.log(response);
                this.questions = response;
                if(this.questions.length == 0){
                  this.message = "No Matches Found!";
                }
              }
            }
          )
        }
      }
    )
  }
  getAnswer(element:any){
    let authority = this.authenticationService.getUserRole();
    if(authority == "[ROLE_ADMIN]")
    this.router.navigate(["admindashboard/answer/"+element.questionId+"/"+this.search]);
    else
    this.router.navigate(["dashboard/answer/"+element.questionId+"/"+this.search]);
  }

  call(question:any){
console.log(question);
  }

}
