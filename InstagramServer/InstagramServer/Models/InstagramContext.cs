using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace InstagramServer.Models
{
    public partial class InstagramContext : DbContext
    {
        public InstagramContext(DbContextOptions<InstagramContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Post> Posts { get; set; }
        public virtual DbSet<UserData> UserData { get; set; }
        public virtual DbSet<User> Users { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

            modelBuilder.Entity<Post>(entity =>
            {
                entity.HasKey(x => x.PostId)
                    .HasName("PK__Posts__21B7B5E2F5ADD9D7");

                entity.Property(e => e.Caption).HasMaxLength(40);

                entity.Property(e => e.DateCreated).HasColumnType("datetime");

                entity.Property(e => e.Image).IsRequired();
            });

            modelBuilder.Entity<UserData>(entity =>
            {
                entity.HasKey(x => x.IdData)
                    .HasName("PK__UserData__F298CC8D9A57E7A4");

                entity.Property(e => e.City).HasMaxLength(20);

                entity.Property(e => e.Description).HasMaxLength(30);

                entity.Property(e => e.Firstname).HasMaxLength(20);

                entity.Property(e => e.Lastname).HasMaxLength(20);

                entity.HasOne(d => d.User)
                    .WithMany()
                    .HasForeignKey(x => x.UserId)
                    .HasConstraintName("FK__UserData__UserId__6C190EBB");
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.HasKey(x => x.UserId)
                    .HasName("PK__Users__1788CC4C274C675F");

                entity.Property(e => e.UserId).ValueGeneratedNever();

                entity.Property(e => e.Email)
                    .IsRequired()
                    .HasMaxLength(1);

                entity.Property(e => e.Password)
                    .IsRequired()
                    .HasMaxLength(1);
                
                entity.Property(e => e.Username)
                    .IsRequired()
                    .HasMaxLength(10);
                
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
