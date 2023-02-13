import {Component, OnInit} from '@angular/core';
import {LinksService} from "./links.service";
import {NgForm} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {Links} from "./links";
import {Visit} from "./visit";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'url-shortener-client';
  visits: Visit[] = [];
  shortUrlAnswer = ""
  longUrlAnswer = ""

  constructor(private linksService: LinksService) {
  }

  ngOnInit(): void {

  }

  public submitAction(addForm: NgForm, type: String): void {
    if (type == "Save") {
      this.setLink(addForm);
    }
    if (type == "Get_visits") {
      this.getVisits();
    }
  }

  public setLink(addForm: NgForm): void {
    if (addForm.value.url == "" ||
      addForm.value.unit == "" ||
      addForm.value.amount == "" ||
      isNaN(addForm.value.amount)) {
      this.showAlert();
    } else {
      this.linksService.setLink(addForm.value).subscribe(
        (response: Links) => {
          this.shortUrlAnswer = response.shortURL;
          addForm.reset();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
          addForm.reset();
        }
      );
    }
  }

  public getVisits(): void {
    this.linksService.getVisits().subscribe(
      (response: Visit[]) => {
        this.visits = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public showAlert(): void {
    alert("Some fields are empty or not filled correctly");
  }


}
