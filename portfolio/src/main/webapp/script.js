// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['I love cats', 'I got to know other countries thanks to math competitions', 'I have 5 siblings', 'My favorite movie is Forrest Gump'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function loadPage(){
    // Add functions that need to run when the page loads
    loadGallery();
}
function loadGallery(){
    const num_cats = 15;
    for(let i = 0; i<num_cats; i++){
        const filename = "cat_"+i+".jpeg";
        const img = document.createElement("img");
        img.setAttribute("src", "images/cats/"+filename);
        document.getElementById('cats-gallery').appendChild(img); 
    }

}

function showContent(section_name){
    let section = document.getElementById(section_name);
    section.classList.toggle('showing');
    section.getElementsByClassName("content")[0].classList.toggle("hidden");
    if(section.classList.contains("showing")){
        section.getElementsByClassName("arrow")[0].src='images/logos/down-arrow.png';
    } else{
        section.getElementsByClassName("arrow")[0].src='images/logos/right-arrow.png'
    }
}
