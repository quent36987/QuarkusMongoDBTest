<script>
import axios from 'axios';
import Post from './Post.vue';

export default {
  data() {
    return {
      // checked: false,
      // allActive : true,
      // myPostsActive : false,
      // idPosts:[{id:1, text:"Post1", media:"media", replyPostId:null, createdAt:"23-01-2023", userId: 1, repostId:null, allReplies:[], allReposts:[]},
      //               {id:2, text:"Post2", media:"media", replyPostId:null, createdAt:"23-01-2023", userId: 1, repostId:null, likes:2, dislikes: 2, allReplies:[{id:4, text:"reply1Post2", media:"media", replyPostId:2, createdAt:"23-01-2023", userId: 1, repostId:null}], allReposts:[]},
      //               {id:3, text:"Post3", media:"media", replyPostId:null, createdAt:"23-01-2023", userId: 1, repostId:null, allReplies:[{id:7, text:"reply1toPost3", media:"media", replyPostId:3, createdAt:"23-01-2023", userId: 1, repostId:null}], allReposts:[{id:6, text:"repost1toPost3", media:"media", replyPostId:null, createdAt:"23-01-2023", userId: 1, repostId:3}]},
      //               {id:4, text:"reply1Post2", media:"media", replyPostId:2, createdAt:"23-01-2023", userId: 1, repostId:null, allReplies:[{id:5, text:"reply1toReply2", media:"media", replyPostId:4, createdAt:"23-01-2023", userId: 1, repostId:null}], allReposts:[]},
      //               {id:5, text:"reply1toReply2", media:"media", replyPostId:4, createdAt:"23-01-2023", userId: 1, repostId:null, allReplies:[], allReposts:[]},
      //               {id:6, text:"repost1toPost3", media:"media", replyPostId:null, createdAt:"23-01-2023", userId: 1, repostId:3, allReplies:[], allReposts:[]},
      //               {id:7, text:"reply1toPost3", media:"media", replyPostId:3, createdAt:"23-01-2023", userId: 1, repostId:null, allReplies:[], allReposts:[]}
      //             ],
      // allPostsList:[],
      // postsFollowed:[{id:3, text:"mypost", media:"media", replyPostId:2, createdAt:"23-01-2023", userId: 1, repostId:1}],
      // myPosts:[{id:3, text:"mypost", media:"media", replyPostId:2, createdAt:"23-01-2023", userId: 1, repostId:1}]

      checked: false,
      allActive: true,
      myPostsActive: false,
      idPosts: [],
      allPostsList: [],
      postsFollowed: [],
      myPosts: []
    }
  },
  props: {
    urls: Object,
  },
  methods: {
    active(nb) {
      if (nb == 0) {
        this.allActive = true;
        this.myPostsActive = false;

      } else {
        this.allActive = false;
        this.myPostsActive = true;
      }
    },
    DFS(post, i, id_marked) {
      console.log(this.idPosts)
      console.log(i);
      id_marked.push(post.id);
      console.log(post);
      console.log(id_marked);
      var objPost = {
        "id": post.id,
        "text": post.text,
        "media": post.media,
        "replyPostId": post.replyPostId,
        "createdAt": post.createdAt,
        "userId": post.userId,
        "repostId": post.repostId,
        "likes": post.likes,
        "dislikes": post.dislikes,
        "allReplies": [],
        "allReposts": []
      };
      if (this.idPosts[i].allReplies.length == 0 && this.idPosts[i].allReposts.length == 0) {
        return objPost;
      }
      if (this.idPosts[i].allReplies.length != 0) {
        for (var j = 0; j < this.idPosts[i].allReplies.length; j++) {
          if (!id_marked.includes(this.idPosts[i].allReplies[j].id)) {
            var k = 0
            for (k; k < this.idPosts.length; k++) {
              if (this.idPosts[k].id == this.idPosts[i].allReplies[j].id) {
                break;
              }
            }
            objPost.allReplies.push(this.DFS(this.idPosts[k], k, id_marked));
          }
        }
      }
      if (this.idPosts[i].allReposts.length != 0) {
        for (var l = 0; l < this.idPosts[i].allReposts.length; l++) {
          if (!id_marked.includes(this.idPosts[i].allReposts[l].id)) {
            var m = 0
            for (m; m < this.idPosts.length; m++) {
              if (this.idPosts[m].id == this.idPosts[i].allReposts[l].id) {
                break;
              }
            }
            objPost.allReposts.push(this.DFS(this.idPosts[m], m, id_marked));
          }
        }
      }
      return objPost;
    },
    getRepliesRepostsFromId() {
      var id_marked = []
      for (var i = 0; i < this.idPosts.length; i++) {
        if (!id_marked.includes(this.idPosts[i].id)) {
          this.allPostsList.push(this.DFS(this.idPosts[i], i, id_marked));
        }
      }
      console.log(this.allPostsList)
    },
    async getAllLikes(postId) {
      var likes = await axios.get(this.urls.post + "/posts/" + postId + "/likes", {
        headers: {
          'X-user-id': '8e2d428e-27bd-47cf-9c91-3a60ffc21447'
        }
      })
          .then((res) => {
            return res.data;
          });
      return likes.length;
    },
    async getAllDislikes(postId) {
      var dislikes = await axios.get(this.urls.post +  "/posts/" + postId + "/unlikes", {
        headers: {
          'X-user-id': '8e2d428e-27bd-47cf-9c91-3a60ffc21447'
        }
      })
          .then((res) => {
            return res.data;
          });
      return dislikes.length;
    },
    async getAllReplies(postId) {
      var replies = await axios.get(this.urls.post +  "/posts/" + postId + "/replies", {
        headers: {
          'X-user-id': '8e2d428e-27bd-47cf-9c91-3a60ffc21447'
        }
      })
          .then((res) => {
            return res.data;
          });
      return replies;
    },
    async getAllReposts(postId) {
      var reposts = await axios.get(this.urls.post +  "/posts/" + postId + "/reposts", {
        headers: {
          'X-user-id': '8e2d428e-27bd-47cf-9c91-3a60ffc21447'
        }
      })
          .then((res) => {
            return res.data;
          });
      return reposts;
    },
    async getAllFollowedPosts(userId) {
      var replies = await axios.get(this.urls.post +  "/posts", {
            headers: {
              'X-user-id': '8e2d428e-27bd-47cf-9c91-3a60ffc21447'
            }
          })
              .then((res) => {
                return res.data;
              })
      ;
      return replies;

    },
    async getAllPosts() {
      const listPosts = await axios.get(this.urls.post +  "/posts", {
        headers: {
          'X-user-id': '8e2d428e-27bd-47cf-9c91-3a60ffc21447'
        }
      })
          .then((res) => {
            return res.data;
          });
      var addingFieldPostsList = [];
      for (var i = 0; i < listPosts.lenght; i++) {
        var objPost = {
          "id": listPosts[i].id,
          "text": listPosts[i].text,
          "media": listPosts[i].media,
          "replyPostId": listPosts[i].replyPostId,
          "createdAt": listPosts[i].createdAt,
          "userId": listPosts[i].userId,
          "repostId": listPosts[i].repostId,
          "likes": undefined,
          "dislikes": undefined,
          "allReplies": [],
          "allReposts": []
        }
        var listAllReplies = await this.getAllReplies(addingFieldPostsList[j].id);
        var listAllReposts = await this.getAllReposts(addingFieldPostsList[i].id);
        objPost.likes = await this.getAllLikes(addingFieldPostsList[i].id)
        objPost.dislikes = await this.getAllLikes(addingFieldPostsList[i].id)
        for (var j = 0; j < listAllReplies.lenght; j++) {
          objPost.allReplies.push(listAllReplies[j]);
        }
        for (var k = 0; k < listAllReposts.lenght; k++) {
          objPost.allReposts.push(listAllReposts[j]);
        }
        this.idPosts.push(objPost);
      }
      this.getRepliesRepostsFromId();
    }
  },
  components: {
    Post
  },
  created() {
    this.getAllPosts();
    // this.getRepliesRepostsFromId();
  }
}
</script>

<template>
  <div class="pannel-posts">
    <div class="pannel-header-posts">
      <div @click="active(0)" :class="{activePost: allActive}">All</div>
      <div @click="active(1)" :class="{activePost: myPostsActive}">My posts</div>
    </div>
    <div v-if="allActive">
      <input type="checkbox" id="followers-only" name="I follow" v-model="checked"/>
      <label for="followers-only">I follow</label>
    </div>
    <div class="pannel-body-posts">
      <Post v-if="allActive && !checked" v-for="post in allPostsList" :post="post"></Post>
      <Post v-if="allActive && checked" v-for="post in postsFollowed" :post="post"></Post>
      <Post v-if="myPostsActive" v-for="post in myPosts" :post="post"></Post>
    </div>
  </div>
</template>

<style>
.pannel-posts {
  margin-top: 0;
  padding-top: 0;
  font-family: Arial, Helvetica, sans-serif;
  width: 100%;
  height: fit-content;
}

.pannel-header-posts {
  width: 100%;
  display: flex;
  flex-direction: row;
  height: fit-content;
}

.pannel-header-posts > div {
  border-radius: 20px;
  width: fit-content;
  justify-content: center;
  text-align: center;
  padding: 0.5em;
  margin: 0.25em;
  font-size: large;
  box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 3px, rgba(0, 0, 0, 0.24) 0px 1px 2px;
}

.pannel-header-posts > div:hover {
  color: white;
  background-color: #3D518C;
}

.activePost {
  color: white;
  background-color: #3D518C;
}
</style>