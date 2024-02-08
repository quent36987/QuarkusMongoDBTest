<script>
export default {
    props:['post'],
    data(){
      return{
        likeActive:false,
        dislikeActive:false
      }
    },
    methods:{
      active(nb){
        console.log("clicked")
        if(nb==0)
        { 
          if (!this.likeActive)
          {
            console.log("like clicked and already active")
            this.likeActive = true;
          }
          else if(this.likeActive)
          {
            this.likeActive = false;
          }

          this.dislikeActive = false;
        }
        else if(nb==1) 
        {
          if (this.dislikeActive)
          {
            console.log("dislike clicked and already active")
            this.dislikeActive = false;
          }
          else if(!this.dislikeActive)
          {
            console.log("dislike clicked and not active")
            this.dislikeActive = true;
          }
          this.likeActive = false;
        }
      }
    }
}
</script>
<template>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <div class="post">
        <div class="post-header">
            <p>Posted by: {{ post.userId }}</p>
            <p>at: {{ post.createdAt }}</p>
        </div>
        <div class="post-body">
            <p>{{ post.text }}</p>
        </div>
        <div class="post-bottom">
            <div class="likes-dislikes">
              <span @click="active(0)" v-if="!likeActive" id="like" class="material-symbols-outlined">favorite</span>
              <span @click="active(0)" v-else id="like-active" class="material-symbols-outlined">favorite</span>
              <p class="counter">{{ post.likes }}</p>
            </div>
            <div class="likes-dislikes">
              <span @click="active(1)" v-if="!dislikeActive" class="material-symbols-outlined">heart_broken</span>
              <span @click="active(1)" v-else id="dislike-active" class="material-symbols-outlined">heart_broken</span>
              <p class="counter">{{ post.dislikes }}</p>
            </div>
        </div>
    </div>
    <div class="reposts">
        <Post v-for="repost in post.allReposts" :post="repost"></Post>
    </div>
    <div class="replies">
        <Post v-for="reply in post.allReplies" :post="reply"></Post>
    </div>
    
</template>

<style>
.post{
    width: 100%;
    border-radius: 40px;
    margin: 1em;
    padding-bottom: 1em;
    background-color: ghostwhite;
    box-shadow: rgba(0, 0, 0, 0.19) 0px 10px 20px, rgba(0, 0, 0, 0.23) 0px 6px 6px;
}
.post-header{
    display: flex;
    padding: 0.5em;
    color: white;
    background-color: #3D518C;
    border-top-right-radius: 40px;
    border-top-left-radius: 40px;
    flex-direction: row;
    align-items: center;
    justify-content: left;
}
.post-header > p{
    padding-right: 0.5em;
    padding-left: 0.5em;
    color: #202a4a;
}
.post-body{
    padding: 0.5em;
    font-size: medium;
    box-shadow: rgba(27, 31, 35, 0.04) 0px 1px 0px, rgba(255, 255, 255, 0.25) 0px 1px 0px inset;

}
.replies, .reposts{
  margin-left: 2em;
  display: flex;
  flex-direction: column;
}
.post-bottom{
    display: flex;
    padding: 0.5em;
    flex-direction: row;
    justify-content: space-around;
}
.material-symbols-outlined {
  color: grey;
  font-variation-settings:
  'FILL' 0,
  'wght' 400,
  'GRAD' 0,
  'opsz' 24
}
#like-active{
  color: red;
  font-variation-settings:
  'FILL' 1,
  'wght' 400,
  'GRAD' 0,
  'opsz' 24
}
#dislike-active{
  color: black;
  font-variation-settings:
  'FILL' 1,
  'wght' 400,
  'GRAD' 0,
  'opsz' 24
}
#like{
  color: red;
}
.likes-dislikes{
  display: flex;
  flex-direction: row;
  align-items: center;
  font-size: medium;

}
.counter{
  margin-left: 1em;
  color: #949494;
}
</style>
