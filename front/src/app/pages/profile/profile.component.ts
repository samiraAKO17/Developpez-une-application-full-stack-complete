import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { Topic } from 'src/app/models/topic.model';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user!: User;
  topics: Topic[] = [];
  profileForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private topicService: TopicService
  ) {}

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.initForm(); // Initialise le formulaire avec les valeurs
    });

    this.topicService.getUserTopics().subscribe(topics => this.topics = topics);
  }

  initForm(): void {
    this.profileForm = this.fb.group({
      name: [this.user.name, Validators.required],
      username: [this.user.username, [Validators.required, Validators.email]],
      password: [
        this.user.password,
        [
          Validators.required,
          Validators.minLength(8),
          this.passwordValidator
        ]
      ]
    });
  }

  save(): void {
    if (this.profileForm.valid) {
      const updatedUser: User = {
        ...this.user,
        ...this.profileForm.value
      };
      this.userService.updateProfile(updatedUser).subscribe();
    }
  }

  unsubscribe(topicId: number): void {
    this.topicService.unsubscribeFromTopic(topicId).subscribe(() => {
      this.topics = this.topics.filter(t => t.id !== topicId);
    });
  }

  passwordValidator(control: any) {
    const value = control.value;
    if (!value) return null;

    const hasUpperCase = /[A-Z]/.test(value);
    const hasLowerCase = /[a-z]/.test(value);
    const hasNumeric = /[0-9]/.test(value);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);

    const isValid = hasUpperCase && hasLowerCase && hasNumeric && hasSpecialChar;

    return isValid ? null : { passwordStrength: true };
  }
}
