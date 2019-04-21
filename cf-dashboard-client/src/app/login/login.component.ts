import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { Router } from "@angular/router";
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl
} from "@angular/forms";
import {
  HttpClient,
  HttpParams,
  HttpHeaders,
  HttpResponse,
  HttpRequest,
  HttpErrorResponse
} from "@angular/common/http";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"]
})
export class LoginComponent implements OnInit {
  private myForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private httpClient: HttpClient,
    private router: Router
  ) {
    this.myForm = this.fb.group({
      userId: this.fb.control("User id", Validators.required),
      userPassword: this.fb.control("Password", Validators.required)
    });
    this.httpClient = httpClient;
    this.router = router;
  }

  ngOnInit() {}
  onSubmit() {
    console.log(this.myForm.value);
    this.initDashBoard();
  }

  initDashBoard() {
    let login = {
      userName: this.myForm.value.userId,
      password: this.myForm.value.userPassword
    };
    // this.httpClient
    //   .post("http://localhost:8080/cfdashboard/login", login)
    //   .subscribe(
    //     res => {
    //       console.log(res['body']);
    //       this.router.navigate(["/home"]);
    //     },
    //     (err: HttpErrorResponse) => {
    //       console.log(err.message);
    //     }
    //   );
    let organizations: any;
    this.httpClient
      .get("../../../assets/mockjson/organizations.json")
      .subscribe(data => {
        organizations = data;
      });

    console.log(organizations);
    this.router.navigate(["/home"]);
  }
}
