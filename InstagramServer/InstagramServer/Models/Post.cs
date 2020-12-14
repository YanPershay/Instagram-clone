using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace InstagramServer.Models
{
    public partial class Post
    {
        public int PostId { get; set; }
        public Guid UserId { get; set; }
        public string Caption { get; set; }
        public string Image { get; set; }
        public DateTime DateCreated { get; set; }
        public virtual User User { get; set; }
    }
}
